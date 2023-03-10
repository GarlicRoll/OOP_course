package com.example.oop_course.repository;

import com.example.oop_course.model.Driver;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepository extends CrudRepository<Driver, Long> {

}
