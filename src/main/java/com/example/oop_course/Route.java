package com.example.oop_course;

import java.util.ArrayList;
import java.util.List;

public class Route extends Object {
    private List<Point> points;
    private List<Bus> buses;
    private int number;
    private int start;
    private static List<Route> routes;

    public boolean addBus(Bus bus) {
        return false;
    }

    public boolean addPoint(Point point) {
        return false;
    }

    public int getNumber() {
        return number;
    }

    public boolean setNumber(int number) {
        this.number = number;
        return true;
    }

    public List<Bus> getBuses() {
        return new ArrayList<>();
    }

    private boolean setBuses(List<Bus> buses) {
        this.buses = buses;
        return true;
    }

    public List<Point> getPoints() {
        return new ArrayList<>();
    }

    private boolean setPoints(List<Point> points) {
        this.points = points;
        return true;
    }

    public boolean removePoint(Point point) {
        return false;
    }

    public boolean removeBus(Bus bus) {
        return false;
    }

    private boolean addRoute(Route route) {
        return false;
    }

    private boolean removeRoute(Route route) {
        return false;
    }

    public static List<Route> getRoutes() {
        return new ArrayList<>();
    }

    public boolean setStart(int start) {
        this.start = start;
        return true;
    }

    public int getStart() {
        return start;
    }
}
