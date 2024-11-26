package com.example.configuration;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EntityScan(basePackages = "com.example.domain") 
public class WebConfig implements WebMvcConfigurer {

	//Bean para la configuracion CORS, que permite acceso del frontend al backend
	//establezco la url del frontend, las cabeceras y metodos que se van a aceptar
	//tambien establezco tiempo de validez
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:8100");
		config.setAllowedHeaders(Arrays.asList(
				HttpHeaders.AUTHORIZATION,
				HttpHeaders.CONTENT_TYPE,
				HttpHeaders.ACCEPT
		));
		config.setAllowedMethods(Arrays.asList(
				HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name()		
		));
		config.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(-102);
		return bean;
	}
	
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
