package com.example.oop_course.controller;

import com.example.oop_course.repository.DriverRepository;
import com.example.oop_course.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class DriverController {
    @Autowired
    private DriverRepository driverRepository;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required = false, defaultValue = "World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Driver> drivers = driverRepository.findAll();

        model.put("drivers", drivers);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String name, @RequestParam String category, Map<String, Object> model) {
        Driver driver = new Driver(name, category);

        driverRepository.save(driver);

        Iterable<Driver> drivers = driverRepository.findAll();

        model.put("drivers", drivers);

        return "main";
    }
}
