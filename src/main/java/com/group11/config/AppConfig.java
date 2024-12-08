package com.group11.config;

import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
