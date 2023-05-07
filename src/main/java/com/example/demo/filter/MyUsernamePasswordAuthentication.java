package com.example.demo.filter;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@PreFilter("doLogin")
public class MyUsernamePasswordAuthentication extends AbstractAuthenticationProcessingFilter {
    @Resource
    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");
    public MyUsernamePasswordAuthentication() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest r=(HttpServletRequest)request;
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        if (antPathMatcher.match("/doLogin", r.getRequestURI())) {
            System.out.println(r.getRequestURI());
            if(request.getParameter("verifyCode").equals(r.getSession().getAttribute("verifyCode"))) {
                chain.doFilter(request, response);
            }
            else {
                HttpServletResponse r1=(HttpServletResponse)response;
                r1.setStatus(302);
                r1.sendRedirect("/login?errorCode");

            }
        }else  chain.doFilter(request, response);






    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }

}
