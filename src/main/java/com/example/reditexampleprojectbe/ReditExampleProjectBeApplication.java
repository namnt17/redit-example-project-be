package com.example.reditexampleprojectbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReditExampleProjectBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReditExampleProjectBeApplication.class, args);
    }

}
