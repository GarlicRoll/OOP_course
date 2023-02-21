package com.example.oop_course;

import java.util.List;

/**
 * Финальный класс маршрута с полями buses, number, id, violations
 *
 * @author Пименов Глеб
 */
public final class Route {
    /**
     * Поле id
     */
    private int id;
    /**
     * Поле автобусы
     */
    private List<Bus> buses;
    /**
     * Поле номер маршрута
     */
    private int number;
    /**
     * Поле нарушения
     */
    private List<String> violations;

    /**
     * Функция получения значения поля {@link Route#number}
     *
     * @return возвращает номер маршрута
     */
    public int getNumber() {
        return number;
    }

    /**
     * Функция получения значения поля {@link Route#buses}
     *
     * @return возвращает список автобусов на маршруте
     */
    public List<Bus> getBuses() {
        return buses;
    }

    /**
     * Функция получения значения поля {@link Route#id}
     *
     * @return возвращает id
     */
    public int getId() {
        return id;
    }

    /**
     * Функция получения значения поля {@link Route#violations}
     *
     * @return возвращает нарушения
     */
    public List<String> getViolations() {
        return violations;
    }
}
