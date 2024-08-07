package dev.prabhjotaulakh.fuel.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import dev.prabhjotaulakh.fuel.api.services.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {
    private final UnauthorizedRequestHandler unauthorizedRequestHandler;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(UnauthorizedRequestHandler unauthorizedRequestHandler, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.unauthorizedRequestHandler = unauthorizedRequestHandler;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
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

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
	public AuthenticationManager authenticationManager(BCryptPasswordEncoder bCryptPasswordEncoder) {
		var authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);

		return new ProviderManager(authenticationProvider);
	}
}
