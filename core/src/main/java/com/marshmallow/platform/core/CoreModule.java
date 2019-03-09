package com.marshmallow.platform.core;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CoreModule {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
