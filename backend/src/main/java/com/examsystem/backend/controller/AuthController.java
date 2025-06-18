package com.examsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.examsystem.backend.dto.user.UserLogin;
import com.examsystem.backend.pojo.ResponseMessage;
import com.examsystem.backend.security.CustomUserDetails;
import com.examsystem.backend.security.CustomUserDetailsService;
import com.examsystem.backend.utils.JwtUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService  customUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseMessage<String> authenticateUser(@Validated @RequestBody UserLogin loginDto) {
        // 1. 执行认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUid(),
                        loginDto.getPassword()));

        // 2. 设置安全上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. 生成JWT令牌
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateToken(userDetails);

        // 4. 返回响应
        return ResponseMessage.success(jwtToken);
    }
}
