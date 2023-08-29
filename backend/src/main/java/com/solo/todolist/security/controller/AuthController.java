package com.solo.todolist.security.controller;

import com.solo.todolist.member.dto.MemberLoginDTO;
import com.solo.todolist.member.entity.Member;
import com.solo.todolist.member.service.MemberService;
import com.solo.todolist.security.jwt.JwtTokenizer;
import com.solo.todolist.security.jwt.Tokens;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@RequestBody MemberLoginDTO memberLoginDTO) {
        String email = memberLoginDTO.getEmail();
        String password = memberLoginDTO.getPassword();
        Tokens tokens = memberService.loginMember(email, password);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<Tokens> refresh(HttpServletRequest request) {
        String token = request.getHeader("RefreshToken");
        String email = jwtTokenizer.getClaims(token, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()))
                .getBody()
                .getSubject();
        Member member = memberService.getVerifiedMember(email);
        List<String> roles = member.getRoles();
        Tokens tokens = jwtTokenizer.generateTokens(email, roles);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }
}
