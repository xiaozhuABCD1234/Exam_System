package com.examsystem.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.examsystem.backend.dto.user.UserLogin;
import com.examsystem.backend.dto.user.UserOut;
import com.examsystem.backend.entity.User;
import com.examsystem.backend.pojo.ResponseMessage;
import com.examsystem.backend.security.CustomUserDetails;
import com.examsystem.backend.utils.DtoUtils;
import com.examsystem.backend.utils.JwtUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    // @Autowired
    // private CustomUserDetailsService customUserDetailsService;

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

    @GetMapping("/me")
    public ResponseMessage<UserOut> getCurrentUser() {
        // 从安全上下文中获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 检查是否已认证
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseMessage.error(401, "未认证，请先登录");
        }

        // 获取用户详情
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser(); // 需要添加getter方法
        if (user == null) {
            return ResponseMessage.error(404, "用户不存在");
        }

        return ResponseMessage.success(DtoUtils.UserToUserOutput(user));
    }
}
