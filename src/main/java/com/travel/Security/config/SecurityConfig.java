package com.travel.Security.config;


import com.travel.Security.Jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;

@Configuration("securityConfig1")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf ->
                        csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/travel/public/categorias/**",
                                        "/travel/public/caracteristicas/**",
                                        "/travel/public/productos/**",
                                        "/travel/public/favoritos/**",
                                        "/travel/public/mail/**",
                                        "/travel/users/**",
                                        "/auth/**",
                                        "/travel/public/fechas-disponibles/**",
                                        "/travel/public/reservas/**"
                                       ).permitAll()
                                //.requestMatchers(HttpMethod.GET, "/algo").hasRole("ADMIN")
                                // .requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cc = new CorsConfiguration();

        // Permitir todos los orígenes solo en desarrollo
        cc.addAllowedOriginPattern("*"); // Usa addAllowedOriginPattern en lugar de addAllowedOrigin para admitir orígenes dinámicos
        cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE"));
        cc.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));
        cc.setExposedHeaders(Arrays.asList("Authorization")); // Si necesitas exponer algún header
        cc.setAllowCredentials(true); // Habilitar cookies o credenciales
        cc.setMaxAge(Duration.ofHours(1)); // Cachear la configuración por 1 hora

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc); // Aplica la configuración a todas las rutas
        return source;
    }





}
