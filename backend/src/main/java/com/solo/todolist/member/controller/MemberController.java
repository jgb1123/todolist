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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Tokens> loginMember(@RequestBody MemberLoginDTO memberLoginDTO) {
        String email = memberLoginDTO.getEmail();
        String password = memberLoginDTO.getPassword();
        Tokens tokens = memberService.loginMember(email, password);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postMember(@RequestBody MemberPostDTO memberPostDTO) {
        Member member = memberMapper.memberPostDTOToMember(memberPostDTO);
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
                                         @RequestBody MemberPatchDTO memberPatchDTO) {
        Member member = memberMapper.memberPatchDTOToMember(memberPatchDTO);
        Member updatedMember = memberService.updateMember(memberId, member);
        MemberResponseDTO memberResponseDTO = memberMapper.memberToMemberResponseDTO(updatedMember);
        return new ResponseEntity<>(new SingleResponseDTO<>(memberResponseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
