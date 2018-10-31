package com.github.alexkirnsu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PAGE = "/login";
    private static final String REGISTRATION_PAGE = "/registration";
    private static final String CSS = "/pages/css/**";
    private static final String FONTS = "/pages/fonts/**";
    private static final String JS = "/pages/js/**";
    private static final String LOGIN_ACTION = "/loginAction";
    private static final String LOGOUT_ACTION = "/logout";
    private static final String SUCCESS_URL = "/notes";
    private static final String FAILED_URL = "/login?error";

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getBCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(LOGIN_PAGE, REGISTRATION_PAGE, CSS, FONTS, JS).permitAll()
                .anyRequest().authenticated()
                .and();

        http.formLogin()
                .loginPage(LOGIN_PAGE)
                .loginProcessingUrl(LOGIN_ACTION)
                .failureUrl(FAILED_URL)
                .defaultSuccessUrl(SUCCESS_URL, true)
                .permitAll();

        http.logout()
                .permitAll()
                .logoutUrl(LOGOUT_ACTION)
                .logoutSuccessUrl(LOGIN_PAGE)
                .invalidateHttpSession(true);
    }
}