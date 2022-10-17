package com.example.springjwtsecuritysql.security;

import com.example.springjwtsecuritysql.core.Constants;
import com.example.springjwtsecuritysql.security.jwt.JWTConfigurer;
import com.example.springjwtsecuritysql.security.jwt.TokenProvider;
import com.example.springjwtsecuritysql.service.auth2.CustomOAuth2UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    CorsFilter corsFilter;

    TokenProvider tokenProvider;

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    public SecurityConfig(CorsFilter corsFilter, TokenProvider tokenProvider, CustomOAuth2UserService oAuth2UserService) {
        this.corsFilter = corsFilter;
        this.tokenProvider = tokenProvider;
        this.oAuth2UserService = oAuth2UserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/api/login/**", "/api/token/refresh/**").permitAll()
                .antMatchers("/api/users").permitAll()
                .antMatchers("/api/users/**").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/", "/login", "/oauth/**").permitAll()
                .antMatchers(Constants.Api.Path.AUTH + "/**").permitAll()
                .antMatchers("/api/logout").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/admin/**").hasAnyAuthority(Constants.Role.ADMIN, Constants.Role.JE)
                .antMatchers("/api/user/**").hasAnyRole(Constants.Role.JE, Constants.Role.ADMIN, Constants.Role.USER)
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/", "/login", "/oauth/**").permitAll().antMatchers("/", "/login", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .apply(securityConfigurerAdapter())
                .and()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oAuth2UserService);
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
