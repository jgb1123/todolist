package com.solo.todolist.member.mapper;

import com.solo.todolist.member.dto.MemberLoginDTO;
import com.solo.todolist.member.dto.MemberPatchDTO;
import com.solo.todolist.member.dto.MemberPostDTO;
import com.solo.todolist.member.dto.MemberResponseDTO;
import com.solo.todolist.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member memberPostDTOToMember(MemberPostDTO memberPostDTO) {
        return Member.builder()
                .email(memberPostDTO.getEmail())
                .password(memberPostDTO.getPassword())
                .name(memberPostDTO.getName())
                .build();
    }

    public Member memberPatchDTOToMember(MemberPatchDTO memberPatchDTO) {
        return Member.builder()
                .password(memberPatchDTO.getPassword())
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
