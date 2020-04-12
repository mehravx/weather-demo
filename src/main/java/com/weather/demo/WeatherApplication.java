package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.demo.config")
public class WeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

}
