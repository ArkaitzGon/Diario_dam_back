package com.example.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EntityScan(basePackages = "com.example.domain") 
public class WebConfig implements WebMvcConfigurer {

	/*
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Maneja solo los recursos estáticos que estén en la carpeta /static/
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        
        // Elimina este mapeo global para las rutas de la API
        // registry.addResourceHandler("/**")
        //         .addResourceLocations("classpath:/static/") // Esto evita que las rutas de la API sean tratadas como estáticas
        //         .resourceChain(false);
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Puedes añadir interceptores si es necesario, pero esto debería ser suficiente en este caso.
    }*/
}
