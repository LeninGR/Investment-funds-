package com.investment.funds.infrastructure.configuration;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleLoggingFilter extends OncePerRequestFilter {

    private final ApplicationContext applicationContext;

    public SimpleLoggingFilter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out
                .println(">>> SPRING FILTER: Request received: " + request.getMethod() + " " + request.getRequestURI());

        // Debug de DispatcherServlet
        boolean hasDispatcher = applicationContext.containsBean("dispatcherServlet");
        System.out.println(">>> SPRING FILTER: Has dispatcherServlet bean? " + hasDispatcher);
        if (hasDispatcher) {
            DispatcherServlet ds = applicationContext.getBean(DispatcherServlet.class);
            System.out.println(">>> SPRING FILTER: DispatcherServlet mappings: " + ds.getHandlerMappings());
        } else {
            System.out.println(">>> SPRING FILTER: WARNING! No DispatcherServlet found!");
            String[] beanNames = applicationContext.getBeanDefinitionNames();
            System.out.println(">>> SPRING FILTER: Total beans: " + beanNames.length);
            System.out.println(">>> SPRING FILTER: Bean names: " + java.util.Arrays.toString(beanNames));
        }

        System.out.println(">>> SPRING FILTER: Context Path: " + request.getContextPath());
        System.out.println(">>> SPRING FILTER: Servlet Path: " + request.getServletPath());
        System.out.println(">>> SPRING FILTER: Path Info: " + request.getPathInfo());
        System.out.println(">>> SPRING FILTER: Query String: " + request.getQueryString());

        filterChain.doFilter(request, response);

        System.out.println(">>> SPRING FILTER: Response status: " + response.getStatus());
    }
}
