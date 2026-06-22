package com.quickStart.quickStart.config;

import com.quickStart.quickStart.jwtSecurity.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/signup",
                                "/auth/login").permitAll().
                        requestMatchers(HttpMethod.GET, "/employees").hasRole("USER").
                        requestMatchers(HttpMethod.POST, "/employees").hasAnyRole("ADMIN", "USER").
                        requestMatchers(HttpMethod.DELETE, "/employees/{employeeId}").hasRole("ADMIN").
                        requestMatchers(HttpMethod.PUT, "/employees/{employeeId}")
                        .hasAnyRole("ADMIN", "USER").requestMatchers(HttpMethod.GET, "/employees/{employeeId}")
                        .hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
