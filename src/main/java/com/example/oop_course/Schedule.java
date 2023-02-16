package com.example.oop_course;

import java.util.ArrayList;
import java.util.List;

public final class Schedule {
    private List<List<Integer>> adjacencyMatrixOfPoints;
    private List<String> violations;

    private boolean setAdjacencyMatrixOfPoints(List<List<Integer>> adjacencyMatrixOfPoints) {
        this.adjacencyMatrixOfPoints = adjacencyMatrixOfPoints;
        return true;
    }

    public List<List<Integer>> getAdjacencyMatrixOfPoints() {
        return adjacencyMatrixOfPoints;
    }

    public boolean addPoint(Point point, List<Integer> timings) {
        return false;
    }

    public List<Integer> getTimings(Route route) {
        return new ArrayList<>();
    }

    private boolean setViolations(List<String> violations) {
        this.violations = violations;
        return true;
    }

    public List<String> getViolations() {
        return violations;
    }

    public boolean addViolation(String violation) {
        return false;
    }

    public boolean clearViolations() {
        return false;
    }
}
