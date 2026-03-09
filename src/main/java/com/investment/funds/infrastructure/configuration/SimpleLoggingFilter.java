package com.investment.funds.infrastructure.configuration;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println(">>> SPRING FILTER: Request received: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println(">>> SPRING FILTER: Context Path: " + request.getContextPath());
        System.out.println(">>> SPRING FILTER: Servlet Path: " + request.getServletPath());
        
        filterChain.doFilter(request, response);
        
        System.out.println(">>> SPRING FILTER: Response status: " + response.getStatus());
    }
}
