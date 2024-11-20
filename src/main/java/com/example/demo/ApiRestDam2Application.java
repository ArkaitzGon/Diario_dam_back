package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example") //Escanea todos los paquetes de esa ruta
@EnableJpaRepositories(basePackages = "com.example.repository")  // Asegura que los repositorios sean escaneados
public class ApiRestDam2Application {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestDam2Application.class, args);
	}

}
