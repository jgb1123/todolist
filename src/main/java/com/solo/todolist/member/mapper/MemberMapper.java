package com.solo.todolist.member.mapper;

import com.solo.todolist.member.dto.MemberPatchDto;
import com.solo.todolist.member.dto.MemberPostDto;
import com.solo.todolist.member.dto.MemberResponseDto;
import com.solo.todolist.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member memberPostDtoToMember(MemberPostDto memberPostDto) {
        return Member.builder()
                .email(memberPostDto.getEmail())
                .password(memberPostDto.getPassword())
                .name(memberPostDto.getName())
                .build();
    }

    public Member memberPatchDtoToMember(MemberPatchDto memberPatchDto) {
        return Member.builder()
                .password(memberPatchDto.getPassword())
                .build();
    }

    public MemberResponseDto memberToMemberResponseDto(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
