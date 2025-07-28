package com.example.finnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FinnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinnectApplication.class, args);
    }

}
