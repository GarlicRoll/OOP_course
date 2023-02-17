package com.example.oop_course;

import java.util.List;

/**
 * Финальный класс водителя, с полями adjacencyMatrixOfPoints и violations
 *
 * @author Пименов Глеб
 */
public final class Schedule {
    /**
     * Поле матрица смежности интервалов остановок
     */
    private List<List<Integer>> adjacencyMatrixOfPoints;
    /**
     * Поле нарушения графика
     */
    private List<String> violations;

    /**
     * Функция получения значения поля {@link Schedule#getAdjacencyMatrixOfPoints()}
     *
     * @return возвращает матрицу смежности интервалов остановок
     */
    public List<List<Integer>> getAdjacencyMatrixOfPoints() {
        return adjacencyMatrixOfPoints;
    }

    /**
     * Функция получения значения поля {@link Schedule#getViolations()}
     *
     * @return возвращает нарушения графика
     */
    public List<String> getViolations() {
        return violations;
    }
}
