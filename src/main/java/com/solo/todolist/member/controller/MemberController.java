package com.solo.todolist.member.controller;

import com.solo.todolist.dto.SingleResponseDTO;
import com.solo.todolist.member.dto.MemberPatchDTO;
import com.solo.todolist.member.dto.MemberPostDTO;
import com.solo.todolist.member.dto.MemberResponseDTO;
import com.solo.todolist.member.entity.Member;
import com.solo.todolist.member.mapper.MemberMapper;
import com.solo.todolist.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @GetMapping
    public String test() {
        return "test";
    }

    @PostMapping
    public ResponseEntity<?> postMember(@RequestBody MemberPostDTO memberPostDto) {

        Member member = memberMapper.memberPostDTOToMember(memberPostDto);
        Member savedMember = memberService.createMember(member);
        MemberResponseDTO memberResponseDto = memberMapper.memberToMemberResponseDTO(savedMember);
        return new ResponseEntity<>(new SingleResponseDTO<>(memberResponseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable Long memberId) {
        Member foundMember = memberService.findMember(memberId);
        MemberResponseDTO memberResponseDto = memberMapper.memberToMemberResponseDTO(foundMember);
        return new ResponseEntity<>(new SingleResponseDTO<>(memberResponseDto), HttpStatus.OK);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<?> patchMember(@PathVariable Long memberId,
                                         @RequestBody MemberPatchDTO memberPatchDto) {
        Member member = memberMapper.memberPatchDTOToMember(memberPatchDto);
        Member updatedMember = memberService.updateMember(memberId, member);
        MemberResponseDTO memberResponseDto = memberMapper.memberToMemberResponseDTO(updatedMember);
        return new ResponseEntity<>(new SingleResponseDTO<>(memberResponseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
