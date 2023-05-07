package com.example.demo.filter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "KillerUserRoleFilter", urlPatterns = "/index")

public class UserRoleFilter implements Filter{



    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 使用authentication参数
        // ...
        HttpServletRequest r=(HttpServletRequest)request;
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (antPathMatcher.match("/form", r.getRequestURI())) {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            String username = authentication.getAuthorities().toString();
            System.out.println(username);
            if(username.equals("[ROLE_user]")&&r.getParameter("uid")!=null)
            {
                HttpServletResponse r1=(HttpServletResponse)response;
                r1.setStatus(302);
                r1.sendRedirect("/error");
            }
        }
        chain.doFilter(request, response);
    }
}
