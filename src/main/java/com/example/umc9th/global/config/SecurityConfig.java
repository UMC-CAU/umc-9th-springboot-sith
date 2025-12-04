package com.example.umc9th.global.config;

import com.example.umc9th.global.auth.jwt.AuthenticationEntryPointImpl;
import com.example.umc9th.global.auth.jwt.JwtAuthFilter;
import com.example.umc9th.global.auth.jwt.JwtUtil;
import com.example.umc9th.global.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    private final String[] allowUrls = {
            "/sign-up",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/login"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(allowUrls).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
//                .formLogin(form->form
//                        .defaultSuccessUrl("/",true)
//                        .permitAll())
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint()));
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }
}
