package com.example.oop_course;

import java.util.ArrayList;
import java.util.List;

public final class Point extends Object {
    private int x;
    private int y;
    private String name;
    private static List<Point> points;

    public boolean setX(int x) {
        this.x = x;
        return true;
    }

    public boolean setY(int y) {
        this.y = y;
        return true;
    }

    public boolean setName(String name) {
        this.name = name;
        return true;
    }

    private boolean addPoint(Point point) {
        return false;
    }

    private boolean removePoint(Point point) {
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public static List<Point> getPoints() {
        return new ArrayList<>();
    }
}
