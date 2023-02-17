package com.example.oop_course;

import java.util.List;

public final class Route extends Object {
    private List<Point> points;
    private List<Bus> buses;
    private int number;
    private int start;

    public int getNumber() {
        return number;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public List<Point> getPoints() {
        return points;
    }

    public int getStart() {
        return start;
    }
}
