package com.solo.todolist.member.service;

import com.solo.todolist.exception.BusinessLogicException;
import com.solo.todolist.exception.ExceptionCode;
import com.solo.todolist.member.entity.Member;
import com.solo.todolist.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member findMember(Long memberId) {
        return getVerifiedMember(memberId);
    }

    public Member updateMember(Long memberId, Member member) {
        Member foundMember = getVerifiedMember(memberId);
        foundMember.changeInfo(member);
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
