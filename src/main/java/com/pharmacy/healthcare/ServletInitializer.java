package com.pharmacy.healthcare;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.out.println("ServletInitializer called.");
        return application.sources(HealthcareApplication.class);
    }
}
