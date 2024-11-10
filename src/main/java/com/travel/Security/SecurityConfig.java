package com.travel.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  // Anotación que indica que esta clase es una clase de configuración
@EnableWebSecurity   // Habilita la seguridad en la aplicación
public class SecurityConfig  {

     // Configura las rutas y los permisos de acceso
   /*  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()   // Deshabilita la protección CSRF (opcional, dependiendo de tu aplicación)
            .authorizeRequests()
            .antMatchers("/login", "/register").permitAll() // Permite acceso sin autenticación a las páginas de login y register
            .antMatchers("/admin/**").hasRole("ADMIN") // Solo los administradores pueden acceder a /admin/*
            .anyRequest().authenticated() // Cualquier otra solicitud debe estar autenticada
            .and()
            .formLogin()
            .loginPage("/login") // Página de inicio de sesión personalizada
            .permitAll()   // Permite acceso a la página de login sin necesidad de autenticarse
            .and()
            .logout()   // Configura el logout
            .permitAll();   // Permite acceso al logout
    }

     // Configura la autenticación, incluyendo el servicio y el codificador de contraseñas
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());   // Utiliza el servicio de detalles del usuario y el codificador de contraseñas
    }

    // Define el codificador de contraseñas que se usará (BCrypt es común para la seguridad)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usar BCrypt para la codificación de contraseñas
    }*/
}
