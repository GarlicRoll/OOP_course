package com.example.oop_course;

import java.util.List;

/**
 * Финальный класс маршрута, наследуемый от объекта, с полями points, buses, number и start
 *
 * @author Пименов Глеб
 */
public final class Route extends Object {
    /** Поле остановки */
    private List<Point> points;
    /** Поле автобусы */
    private List<Bus> buses;
    /** Поле номер маршрута */
    private int number;
    /** Поле начало маршрута */
    private int start;

    /**
     * Функция получения значения поля {@link Route#number}
     * @return возвращает номер маршрута
     */
    public int getNumber() {
        return number;
    }

    /**
     * Функция получения значения поля {@link Route#buses}
     * @return возвращает список автобусов на маршруте
     */
    public List<Bus> getBuses() {
        return buses;
    }

    /**
     * Функция получения значения поля {@link Route#points}
     * @return возвращает список остановок
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * Функция получения значения поля {@link Route#start}
     * @return возвращает время начала маршрута
     */
    public int getStart() {
        return start;
    }
}
