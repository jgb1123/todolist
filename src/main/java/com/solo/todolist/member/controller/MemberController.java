package com.solo.todolist.member.controller;

import com.solo.todolist.member.dto.MemberPatchDto;
import com.solo.todolist.member.dto.MemberPostDto;
import com.solo.todolist.member.dto.MemberResponseDto;
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
    public ResponseEntity<?> postMember(@RequestBody MemberPostDto memberPostDto) {
        Member member = memberMapper.memberPostDtoToMember(memberPostDto);
        Member savedMember = memberService.createMember(member);
        MemberResponseDto memberResponseDto = memberMapper.memberToMemberResponseDto(savedMember);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable Long memberId) {
        Member foundMember = memberService.findMember(memberId);
        MemberResponseDto memberResponseDto = memberMapper.memberToMemberResponseDto(foundMember);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<?> patchMember(@PathVariable Long memberId,
                                         @RequestBody MemberPatchDto memberPatchDto) {
        Member member = memberMapper.memberPatchDtoToMember(memberPatchDto);
        Member updatedMember = memberService.updateMember(memberId, member);
        MemberResponseDto memberResponseDto = memberMapper.memberToMemberResponseDto(updatedMember);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
