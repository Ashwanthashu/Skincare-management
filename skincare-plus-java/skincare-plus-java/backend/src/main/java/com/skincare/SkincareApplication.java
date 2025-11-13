package com.skincare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.modelmapper.ModelMapper;

/**
 * Main Spring Boot Application Class
 * SkinCare+ - AI-Powered Skin Care Monitoring System
 * 
 * @author Your Name
 * @version 1.0
 */
@SpringBootApplication
public class SkincareApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkincareApplication.class, args);
        System.out.println("\n" +
            "╔═══════════════════════════════════════════════════════╗\n" +
            "║     SkinCare+ Application Started Successfully       ║\n" +
            "║     Backend API running on http://localhost:8080     ║\n" +
            "║     Swagger UI: http://localhost:8080/swagger-ui     ║\n" +
            "╚═══════════════════════════════════════════════════════╝\n");
    }

    /**
     * Bean for making HTTP requests to external APIs
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Bean for object mapping (Entity to DTO conversion)
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
