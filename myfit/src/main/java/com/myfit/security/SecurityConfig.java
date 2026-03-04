package com.myfit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login/**",
                                "/register",
                                "/client/register",
                                "/client/questionnaire",
                                "/client/choose/**",
                                "/css/**",
                                "/js/**",
                                "/uploads/**",
                                "/chat-websocket/**",
                                "/ws/**"
                        ).permitAll()
                        .requestMatchers("/manager/**").hasRole("MANAGER")
                        .requestMatchers("/trainer/**").hasRole("TRAINER")
                        .requestMatchers("/client/**").hasRole("CLIENT")
                        .anyRequest().authenticated()

                )
                .formLogin(login -> login
                        .loginPage("/").permitAll()
                        .loginProcessingUrl("/perform-login") // ✅ важно
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/redirect-after-login", true)

                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
