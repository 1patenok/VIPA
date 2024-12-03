package com.example.vipa.security;

import com.example.vipa.service.AuthenticationService;
import com.example.vipa.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final AuthenticationService userService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable());
//                .authorizeHttpRequests(
//                        authorize -> authorize
//                                .requestMatchers(
//                                        "/cart/**", "/reviews/newReview"
//                                ).hasRole("USER")
//                                .requestMatchers(
//                                        "/categories/newCategory", "/categories/updateCategory", "/categories/{categoryId}/deleteCategory",
//                                        "/items/newItem", "/items/updateItem", "/items/{itemId}/deleteItem",
//                                        "/features/**", "/reviews/{reviewId}/deleteReview"
//                                ).hasRole("ADMIN")
//                                .requestMatchers(
//                                        "/profile/**"
//                                ).hasAnyRole("USER", "ADMIN")
//                                .requestMatchers(
//                                        "/auth/signUp",
//                                        "/clients/{clientId}",
//                                        "/clients",
//                                        "/clients/new"
//                                ).permitAll())
//                .sessionManagement(
//                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .exceptionHandling(
//                        exceptionHandling -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                );

        return http.build();
    }
}