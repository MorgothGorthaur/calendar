package com.example.routine;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
public class RoutineApplication {
    public static void main(String[] args) {
        System.out.println("");
        SpringApplication.run(RoutineApplication.class, args);
    }
}
