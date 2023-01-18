package com.takealook.takealook.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takealook.takealook.dto.SecurityExceptionDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final com.takealook.takealook.jwt.JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("authtoken");
        System.out.println(request);
        System.out.println("authtoken");
        String token = jwtUtil.resolveToken(request);
        System.out.println("11111111111111111");
        if(token != null) {
            System.out.println("22222222222222222222");
            if(!jwtUtil.validateToken(token)){
                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            System.out.println("888888888888888888888888888888");
            Claims info = jwtUtil.getUserInfoFromToken(token);
            setAuthentication(info.getSubject());
        }
        System.out.println("124124124123123124124123");
        filterChain.doFilter(request,response);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    public void setAuthentication(String username) {
        System.out.println("9999999999999999999999999999999");
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        System.out.println("33333333333333333333333333");
        response.setStatus(statusCode);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        System.out.println("44444444444444444444444444");
        try {
            System.out.println("55555555555555555555555555");
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
            System.out.println("666666666666666666666666");
        } catch (Exception e) {
            System.out.println("7777777777777777777777777777");
            log.error(e.getMessage());
        }
    }

}