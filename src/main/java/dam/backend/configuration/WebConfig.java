package dam.backend.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EntityScan(basePackages = "dam.backend.domain") 
public class WebConfig implements WebMvcConfigurer {
    @Value("${dam.app.cors.allowed.origins}")
    private List<String> allowedOrigins;
	
	private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;

    //bean para resolver problemas de la configuracion CORS a nivel global en la app
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(allowedOrigins);
        //config.addAllowedOrigin("https://router.project-osrm.org");
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        config.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        // should be set order to -100 because we need to CorsFilter before SpringSecurityFilter
        bean.setOrder(CORS_FILTER_ORDER);
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
