package com.github.alexkirnsu;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.Filter;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.alexkirnsu.config", "com.github.alexkirnsu"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Filter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
