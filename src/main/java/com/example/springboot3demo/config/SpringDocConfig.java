//package com.example.springboot3demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SpringDocConfig {
//    @Bean
//    public OpenAPI openAPI() {
//    return new OpenAPI()
//        .info(
//            new Info()
//                .title("Coupons")
//                .description("Coupons")
//                .version("0.0.1"));
//    }
//
//    @Bean
//    public GroupedOpenApi groupedOpenApi() {
//        return GroupedOpenApi.builder()
//                .group("backend")
//                .packagesToScan("com.cupon.cuponsmanagement.controller")
//                .build();
//    }
//}