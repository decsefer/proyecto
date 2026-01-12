package com.ipn.mx.administracioneventos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AdministracionEventosApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministracionEventosApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://localhost:*", "https://*.onrender.com", "https://*.netlify.app")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("*")
                        .exposedHeaders("Content-Disposition")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
            @Override
            public void addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry registry) {
                registry.addRedirectViewController("/documentacion/swagger-ui.html", "/documentacion");
            }
        };
    }

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }
 

}
