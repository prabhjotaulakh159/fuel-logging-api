package dev.prabhjotaulakh.fuel.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final UnauthorizedRequestHandler unauthorizedRequestHandler;

    public SecurityConfig(UnauthorizedRequestHandler unauthorizedRequestHandler) {
        this.unauthorizedRequestHandler = unauthorizedRequestHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.requestMatchers("/public/**")
                                               .permitAll()
                                               .anyRequest()
                                               .authenticated())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedRequestHandler));
        return http.build();
    }
}
