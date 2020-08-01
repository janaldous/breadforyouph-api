package com.janaldous.breadforyouph.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
 
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateFormat);
        };
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**")
    	.allowedOrigins("https://localhost:3000", "https://breadforyouph-dev.herokuapp.com", "https://www.breadforyouph.life")
    	.allowedMethods("GET", "POST", "PUT", "OPTIONS")
    	.allowedHeaders("*")
    	.allowCredentials(true);
    }

}
