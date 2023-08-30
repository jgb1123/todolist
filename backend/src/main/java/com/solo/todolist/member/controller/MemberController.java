package com.solo.todolist.member.controller;

import com.solo.todolist.dto.SingleResponseDTO;
import com.solo.todolist.member.dto.MemberLoginDTO;
import com.solo.todolist.member.dto.MemberPatchDTO;
import com.solo.todolist.member.dto.MemberPostDTO;
import com.solo.todolist.member.dto.MemberResponseDTO;
import com.solo.todolist.member.entity.Member;
import com.solo.todolist.member.mapper.MemberMapper;
import com.solo.todolist.member.service.MemberService;
import com.solo.todolist.security.jwt.Tokens;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @PostMapping("/create")
    public ResponseEntity<SingleResponseDTO<MemberResponseDTO>> postMember(@RequestBody MemberPostDTO memberPostDTO) {
        Member member = memberMapper.memberPostDTOToMember(memberPostDTO);
        Member savedMember = memberService.createMember(member);
        MemberResponseDTO memberResponseDto = memberMapper.memberToMemberResponseDTO(savedMember);
        return new ResponseEntity<>(new SingleResponseDTO<>(memberResponseDto), HttpStatus.CREATED);
    }

    @GetMapping("/find")
    public ResponseEntity<SingleResponseDTO<MemberResponseDTO>> getMember(@AuthenticationPrincipal String email) {
        Member foundMember = memberService.findMember(email);
        MemberResponseDTO memberResponseDto = memberMapper.memberToMemberResponseDTO(foundMember);
        return new ResponseEntity<>(new SingleResponseDTO<>(memberResponseDto), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<SingleResponseDTO<MemberResponseDTO>> patchMember(@AuthenticationPrincipal String email,
                                         @RequestBody MemberPatchDTO memberPatchDTO) {
        Member member = memberMapper.memberPatchDTOToMember(memberPatchDTO);
        Member updatedMember = memberService.updateMember(email, member);
        MemberResponseDTO memberResponseDTO = memberMapper.memberToMemberResponseDTO(updatedMember);
        return new ResponseEntity<>(new SingleResponseDTO<>(memberResponseDTO), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> deleteMember(@AuthenticationPrincipal String email) {
        memberService.deleteMember(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
