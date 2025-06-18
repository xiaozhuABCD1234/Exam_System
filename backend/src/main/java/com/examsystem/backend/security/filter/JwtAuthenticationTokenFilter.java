package com.examsystem.backend.security.filter;

import com.examsystem.backend.security.CustomUserDetailsService;
import com.examsystem.backend.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    // 使用构造器注入，移除不必要的 @Autowired
    public JwtAuthenticationTokenFilter(JwtUtils jwtUtils, 
                                       CustomUserDetailsService customUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,   // 添加 @NonNull 注解
            @NonNull HttpServletResponse response, // 添加 @NonNull 注解
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        // 跳过OPTIONS预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            // 1. 从请求头中获取JWT令牌
            String jwt = parseJwt(request);
            
            if (StringUtils.hasText(jwt)) {
                // 2. 验证JWT令牌基本有效性
                if (jwtUtils.validateToken(jwt)) {
                    // 3. 从JWT中获取用户名
                    String username = jwtUtils.getUsernameFromToken(jwt);
                    
                    // 4. 检查安全上下文是否已有认证
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        // 5. 加载用户详细信息
                        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                        
                        // 6. 验证令牌与用户是否匹配
                        if (jwtUtils.validateToken(jwt, userDetails)) {
                            // 7. 创建认证令牌
                            UsernamePasswordAuthenticationToken authentication = 
                                new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                                );
                            
                            // 8. 设置请求详情
                            authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                            );
                            
                            // 9. 设置安全上下文
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // logger.error("无法设置用户认证: {}", e.getMessage());
            // 清除上下文以防部分认证状态
            SecurityContextHolder.clearContext();
        }

        // 10. 继续过滤链
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        // 检查标准Authorization头
        if (StringUtils.hasText(headerAuth)) {
            if (headerAuth.startsWith("Bearer ")) {
                return headerAuth.substring(7);
            }
            return headerAuth; // 也支持不带Bearer前缀的令牌
        }
        
        return null;
    }
}
