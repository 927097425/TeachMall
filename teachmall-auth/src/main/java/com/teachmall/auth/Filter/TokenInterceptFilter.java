package com.teachmall.auth.Filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenInterceptFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 执行过滤链，确保请求被正确处理
        filterChain.doFilter(request, response);

        // 在过滤器链执行完后处理响应
        String token = response.getHeader("Authorization");
        if (token != null) {
            // 处理Token逻辑
            System.out.println("Intercepted Token: " + token);
        }
    }
}
