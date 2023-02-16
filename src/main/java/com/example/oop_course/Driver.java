package com.example.oop_course;

import java.util.List;

public final class Driver extends Object {
    private String name;
    private int age;
    private int experience;
    private String category;
    private static List<Driver> drivers;

    public boolean setName(String name) {
        this.name = name;
        return true;
    }

    public String getName() {
        return name;
    }

    public boolean setAge(int age) {
        this.age = age;
        return true;
    }

    public int getAge() {
        return age;
    }

    public boolean setExperience(int experience) {
        this.experience = experience;
        return true;
    }

    public int getExperience() {
        return experience;
    }

    public boolean setCategory(String category) {
        this.category = category;
        return true;
    }

    public String getCategory() {
        return category;
    }

    private boolean addDriver(Driver driver) {
        return false;
    }

    private boolean removeDriver(Driver driver) {
        return false;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }
}
