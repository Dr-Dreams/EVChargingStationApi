package com.ev.charging.station.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@OpenAPIDefinition
@OpenAPI30
@EnableWebMvc
public class EvChargingStationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvChargingStationApiApplication.class, args);
    }

}
