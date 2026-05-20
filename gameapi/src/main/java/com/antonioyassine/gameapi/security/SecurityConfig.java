package com.antonioyassine.gameapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Configuración de seguridad: GET públicos, escritura protegida con JWT
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactiva CSRF porque es una API REST sin estado
                .csrf(csrf -> csrf.disable())
                // Permite la consola H2 en un iframe
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                // Configuración de autorizaciones
                .authorizeHttpRequests(auth -> auth
                        // Consola H2 pública (solo para desarrollo)
                        .requestMatchers("/h2-console/**").permitAll()
                        // Endpoint de autenticación público
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // Archivos estáticos públicos (página HTML)
                        .requestMatchers("/", "/index.html", "/static/**").permitAll()
                        // Todos los GET de la API son públicos
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                        // POST, PUT, DELETE requieren autenticación
                        .anyRequest().authenticated()
                )
                // Sin sesiones: cada petición lleva su propio token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Registra el filtro JWT antes del filtro de autenticación de Spring
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
