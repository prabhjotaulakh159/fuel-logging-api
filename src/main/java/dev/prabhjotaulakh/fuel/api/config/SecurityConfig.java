package dev.prabhjotaulakh.fuel.api.config;

//import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import dev.prabhjotaulakh.fuel.api.services.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    private final UnauthorizedRequestHandler unauthorizedRequestHandler;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(UnauthorizedRequestHandler unauthorizedRequestHandler, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.unauthorizedRequestHandler = unauthorizedRequestHandler;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final String UNPROTECTED_URLS = "/public/**";
        http
            .authorizeHttpRequests(auth -> auth.requestMatchers(UNPROTECTED_URLS)
                                               .permitAll()
                                               .anyRequest()
                                               .authenticated())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                jwt.decoder(jwtDecoder());
                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter());
            }))
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

    @Bean
    public JwtDecoder jwtDecoder() {
        var secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }

    // @Bean
    // CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration configuration = new CorsConfiguration();
    //     configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    //     configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    //     configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration);
    //     return source;
    // }
}
