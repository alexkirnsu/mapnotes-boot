package com.github.alexkirnsu.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.Filter;

@Configuration
@EnableWebMvc
@ComponentScan("com.github.alexkirnsu")
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp().prefix("/pages/").suffix(".jsp");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Filter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
        return filter;
    }
}