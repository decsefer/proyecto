package com.ipn.mx.administracioneventos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = "com.ipn.mx.administracioneventos.ecommerce")
@EntityScan("com.ipn.mx.administracioneventos.ecommerce.domain")
@EnableJpaRepositories("com.ipn.mx.administracioneventos.ecommerce.repository")
public class AdministracionEventosApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministracionEventosApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200", "https://proyecto-z9eq.onrender.com", "https://proyecto-angularfin.netlify.app")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

}
