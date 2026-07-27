package com.ecommercetech.security.config;

import com.ecommercetech.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    // Configuración principal de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF porque usamos JWT
                .csrf(csrf -> csrf.disable())

                // No guardar sesiones en el servidor (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Definir rutas públicas y protegidas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // login y registro públicos
                        .requestMatchers("/api/products/**").hasAnyAuthority("USER", "ADMIN") // ejemplo: productos accesibles con token
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // solo admin
                        .anyRequest().authenticated() // todo lo demás requiere autenticación
                );

        // Registrar el filtro JWT antes del filtro de autenticación por defecto
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

/*
*/