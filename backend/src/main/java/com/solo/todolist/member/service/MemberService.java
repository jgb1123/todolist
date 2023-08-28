package com.solo.todolist.member.service;

import com.solo.todolist.exception.BusinessLogicException;
import com.solo.todolist.exception.ExceptionCode;
import com.solo.todolist.member.entity.Member;
import com.solo.todolist.member.repository.MemberRepository;
import com.solo.todolist.security.jwt.JwtTokenizer;
import com.solo.todolist.security.jwt.Tokens;
import com.solo.todolist.security.utils.CustomAuthorityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils customAuthorityUtils;
    private final JwtTokenizer jwtTokenizer;

    public Tokens loginMember(String email, String password) {
        Member foundMember = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        List<String> authorities = customAuthorityUtils.createRoles(email);
        return jwtTokenizer.generateTokens(email, authorities);
    }

    public Member createMember(Member member) {
        List<String> roles = customAuthorityUtils.createRoles(member.getEmail());
        member.changeRoles(roles);
        return memberRepository.save(member);
    }

    public Member findMember(Long memberId) {
        return getVerifiedMember(memberId);
    }

    public Member updateMember(Long memberId, Member member) {
        Member foundMember = getVerifiedMember(memberId);
        foundMember.changeMemberInfo(member);
        return foundMember;
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
    public Member getVerifiedMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
