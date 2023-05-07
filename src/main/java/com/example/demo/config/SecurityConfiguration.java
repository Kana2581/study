package com.example.demo.config;

import com.example.demo.entity.SimpleUserInformation;
import com.example.demo.filter.MyUsernamePasswordAuthentication;
import com.example.demo.filter.MyUsernamePasswordAuthenticationFilter;
import com.example.demo.filter.UserRoleFilter;
import com.example.demo.service.UserAuthService;
import com.example.demo.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource
    UserAuthService service;

    @Resource
    DataSource dataSource;
    @Resource
    UserInformationService userInformationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationSuccessHandler handler= (request, response, authentication) -> {
            int uid=userInformationService.loadSimpleUserInformationByAccount(authentication.getName()).getUid();

            Cookie cookie=new Cookie("uid",""+uid);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect("/index");
        };

        // http.antMatcher("/doLogin").addFilterBefore(new MyUsernamePasswordAuthentication(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests().antMatchers("/static/**","/register","/auth/api/**","/logout").permitAll().antMatchers("/index","/**/api/**","/course","/form","/space","/watch","/add-teacher").hasAnyRole("teacher","user").
                antMatchers("/course-management","/project-modification","/project","/up-resource","/up-video").hasAnyRole("teacher").
                and().formLogin().loginPage("/login").loginProcessingUrl("/doLogin").permitAll().defaultSuccessUrl("/index",true).successHandler(handler).and().logout().deleteCookies("uid")
                .and().rememberMe().tokenRepository(new JdbcTokenRepositoryImpl(){{setDataSource(dataSource);}}).
                authenticationSuccessHandler(handler).
                and().sessionManagement().
                and().addFilterAfter(new UserRoleFilter(), UsernamePasswordAuthenticationFilter.class).
                csrf().disable()
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    }
}
