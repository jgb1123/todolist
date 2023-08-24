package com.solo.todolist.member.mapper;

import com.solo.todolist.member.dto.MemberPatchDTO;
import com.solo.todolist.member.dto.MemberPostDTO;
import com.solo.todolist.member.dto.MemberResponseDTO;
import com.solo.todolist.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member memberPostDTOToMember(MemberPostDTO memberPostDto) {
        return Member.builder()
                .email(memberPostDto.getEmail())
                .password(memberPostDto.getPassword())
                .name(memberPostDto.getName())
                .build();
    }

    public Member memberPatchDTOToMember(MemberPatchDTO memberPatchDto) {
        return Member.builder()
                .password(memberPatchDto.getPassword())
                .build();
    }

    public MemberResponseDTO memberToMemberResponseDTO(Member member) {
        return MemberResponseDTO.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
