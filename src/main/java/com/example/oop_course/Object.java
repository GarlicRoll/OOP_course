package com.example.oop_course;

public class Object {
    int id;
    static int idCounter;

    private boolean setId(int id) {
        this.id = id;
        return true;
    }

    public int getId() {
        return id;
    }

    public static int getIdCounter() {
        return idCounter;
    }
}
