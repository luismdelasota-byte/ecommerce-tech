package com.ecommercetech.security.config;

import com.ecommercetech.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // Indica que esta clase define beans de configuracion para Spring
@RequiredArgsConstructor
public class SecurityConfig {

    // Inyectamos el filtro JWT, que se encarga de validar tokens en cada request
    private final JwtFilter jwtFilter;

    // Encriptación de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager para login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuración de CORS para permitir solicitudes desde cualquier frontend (React, Vite, Angular, etc.)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Configuración principal de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Habilitar CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Desactivar CSRF porque usamos JWT
                .csrf(csrf -> csrf.disable())

                // No guardar sesiones en el servidor (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Definir rutas públicas y protegidas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // login y registro públicos
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll() // catálogo público para ver productos sin token
                        .requestMatchers("/api/products/**").hasAnyAuthority("USER", "ADMIN") // creación/modificación requiere token
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // solo admin
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated() // todo lo demás requiere autenticación
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        // Registrar el filtro JWT antes del filtro de autenticación por defecto
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

/*
*/