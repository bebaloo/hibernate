package com.example.task_2_5_hibernate.config;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class GeneratorConfig {
    @Bean
    public FakeValuesService fakeValuesService() {
        return new FakeValuesService(new Locale("en-GB"), new RandomService());
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }
}
