package com.example.vipa.config.security;

import com.example.vipa.service.ClientDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final ClientDetailsService clientDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //.csrf(CsrfConfigurer::disable)
                //.cors(CorsConfigurer::disable)
                // Настройка доступа к конечным точкам
                .authorizeHttpRequests(request -> request
                        // Можно указать конкретный путь, * - 1 уровень вложенности, ** - любое количество уровней вложенности
                        .requestMatchers("/").hasRole("ADMIN")
                        .requestMatchers("/auth/**", "/homepage-guest", "/common/**", "/images/**").permitAll()
                        .requestMatchers("/categories/**", "/category/**").hasRole("ADMIN")
                        .requestMatchers("/homepage-client", "/clients/**", "/posts/**", "/common/**", "/client/**", "/post/**")
                        .hasAnyRole("ADMIN", "CLIENT")
                        .anyRequest().hasRole("CLIENT"))
                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/sign-in")
//                        .loginPage("/homepage-guest")
                        .defaultSuccessUrl("/homepage-client", true)
                        .permitAll())
                .logout(logout -> {
                    logout.logoutUrl("/auth/sign-out");
                    logout.logoutSuccessUrl("/auth/sign-in");
                    logout.permitAll();
                });
                //.authenticationProvider(authenticationProvider());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(clientDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
