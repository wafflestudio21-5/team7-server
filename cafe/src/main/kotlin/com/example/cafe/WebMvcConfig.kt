package com.example.cafe

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:5173", "http://ec2-15-165-161-107.ap-northeast-2.compute.amazonaws.com:5173")
            .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
    }
}