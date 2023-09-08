package com.solo.todolist.member.service;

import com.solo.todolist.common.exception.BusinessLogicException;
import com.solo.todolist.common.exception.ExceptionCode;
import com.solo.todolist.member.entity.Member;
import com.solo.todolist.member.repository.MemberRepository;
import com.solo.todolist.security.jwt.JwtTokenizer;
import com.solo.todolist.security.jwt.Tokens;
import com.solo.todolist.security.utils.CustomAuthorityUtils;
import com.solo.todolist.status.entity.Status;
import com.solo.todolist.status.repository.StatusRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final StatusRepository statusRepository;
    private final CustomAuthorityUtils customAuthorityUtils;
    private final JwtTokenizer jwtTokenizer;
    private final BCryptPasswordEncoder encoder;

    public Member createMember(Member member) {
        checkDuplicatedEmail(member);
        List<String> roles = customAuthorityUtils.createRoles(member.getEmail());

        Status noneStatus = createAndSaveDefaultStatus();

        member.setEncodingPassword(encoder.encode(member.getPassword()));
        member.addStatus(noneStatus);
        member.changeRoles(roles);
        return memberRepository.save(member);
    }

    public Member findMember(String email) {
        return getVerifiedMember(email);
    }

    public Member updateMember(String email, Member member) {
        Member foundMember = getVerifiedMember(email);
        foundMember.changeMemberInfo(member);
        return foundMember;
    }

    public void deleteMember(String email) {
        memberRepository.deleteByEmail(email);
    }

    public Member getVerifiedMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Tokens loginMember(String email, String password) {
        Member foundMember = getVerifiedMember(email);
        if(encoder.matches(password, foundMember.getPassword())) {
            List<String> authorities = customAuthorityUtils.createRoles(email);
            return jwtTokenizer.generateTokens(email, authorities);
        } else {
            throw new BusinessLogicException(ExceptionCode.LOGIN_FAILED);
        }
    }

    private Status createAndSaveDefaultStatus() {
        Status noneStatus = Status.builder().statusName("None").priority(1L).build();
        statusRepository.save(noneStatus);
        return noneStatus;
    }
    private void checkDuplicatedEmail(Member member) {
        if(memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
}
