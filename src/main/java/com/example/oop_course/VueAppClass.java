package com.example.oop_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class },
        scanBasePackages={"com.example.oop_course.repository.DriverRepository",
                "com.example.oop_course.controller.DriverController",
                "resources/templates/greeting.mustache"})
public class VueAppClass {

    public static void main(String[] args) {
        SpringApplication.run(VueAppClass.class, args);
    }
}
