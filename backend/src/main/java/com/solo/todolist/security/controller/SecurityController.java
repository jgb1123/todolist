package com.solo.todolist.security.controller;

import com.solo.todolist.member.service.MemberService;
import com.solo.todolist.security.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SecurityController {
    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;



}
