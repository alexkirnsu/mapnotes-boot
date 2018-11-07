package com.github.alexkirnsu.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.Filter;

@Configuration
@ComponentScan("com.github.alexkirnsu")
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Filter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}