package com.ipn.mx.administracioneventos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ipn.mx.administracioneventos.ecommerce")
@EntityScan("com.ipn.mx.administracioneventos.ecommerce.domain")
@EnableJpaRepositories("com.ipn.mx.administracioneventos.ecommerce.repository")
public class AdministracionEventosApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministracionEventosApplication.class, args);
    }

}
