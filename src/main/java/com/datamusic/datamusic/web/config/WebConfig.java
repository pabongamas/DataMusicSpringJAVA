package com.datamusic.datamusic.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
      @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("----------------entrando a establecer cors -----------------------");
        registry.addMapping("/**") // Aplica a todas las rutas
            .allowedOrigins("http://localhost:3000") // El origen que deseas permitir
            .allowedMethods("GET", "POST","DELETE", "OPTIONS") // Los métodos HTTP permitidos
            .allowedHeaders("Origin", "Content-Type", "Accept","Authorization") // Los encabezados permitidos
            .allowCredentials(true); // Permitir cookies o credenciales
    }
}
