package com.github.szsalyi.globalblue.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.szsalyi.globalblue")
public class GlobalBlueApplication {
    public static void main(String[] args) {
        SpringApplication.run(GlobalBlueApplication.class, args);
    }
}
