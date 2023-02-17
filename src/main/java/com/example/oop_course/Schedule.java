package com.example.oop_course;

import java.util.List;

public final class Schedule {
    private List<List<Integer>> adjacencyMatrixOfPoints;
    private List<String> violations;

    public List<List<Integer>> getAdjacencyMatrixOfPoints() {
        return adjacencyMatrixOfPoints;
    }

    public List<String> getViolations() {
        return violations;
    }
}
