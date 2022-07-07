package com.micropos.carts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
public class CartsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartsApplication.class, args);
    }

//    @Bean
//    RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("*")
//                        .allowedHeaders("Access-Control-Allow-Origin", "*")
//                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
//            }
//        };
//    }
}