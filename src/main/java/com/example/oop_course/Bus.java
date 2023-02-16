package com.example.oop_course;

import java.util.ArrayList;
import java.util.List;

public class Bus extends Object {
    private int number;
    private Driver driver;
    private static ArrayList<Bus> buses;

    public boolean setNumber(int number) {
        this.number = number;
        return true;
    }

    public int getNumber() {
        return number;
    }

    public boolean setDriver(Driver driver) {
        this.driver = driver;
        return true;
    }

    public Driver getDriver() {
        return driver;
    }

    private boolean addBus(Bus bus) {
        return false;
    }

    private boolean removeBus(Bus bus) {
        return false;
    }

    public List<Integer> getTimings() {
        return new ArrayList<>();
    }
}
