package com.sufiyandev.BlogShare.security;

import com.sufiyandev.BlogShare.user.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private JWTAuthenticationFilter jwtAuthenticationFilter;
    private JWTService jwtService;
    private UserService userService;

    public AppSecurityConfig(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.jwtAuthenticationFilter = new JWTAuthenticationFilter(
                new JWTAuthenticationManager(jwtService, userService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users", "/users/signup").permitAll()

                // TODO: Remove this(/users/all) in production
                .antMatchers(HttpMethod.GET, "/users", "/users/all").permitAll()

                .antMatchers(HttpMethod.GET, "/blogs", "/blogs/*").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);

    }


}