package com.example.oop_course;

/**
 * Финальный класс автобуса с полями id, number, start, end и driver
 *
 * @author Пименов Глеб
 */
public final class Bus {
    /**
     * Поле id
     */
    private int id;
    /**
     * Поле номера автобуса
     */
    private int number;
    /**
     * Поле время начала работы автобуса
     */
    private int start;
    /**
     * Поле время окончания работы автобуса
     */
    private int end;
    /**
     * Поле водителя автобуса
     */
    private Driver driver;

    /**
     * Функция получения значения поля {@link Bus#id}
     *
     * @return возвращает id
     */
    public int getId() {
        return id;
    }

    /**
     * Функция получения значения поля {@link Bus#number}
     *
     * @return возвращает номер автобуса
     */
    public int getNumber() {
        return number;
    }

    /**
     * Функция получения значения поля {@link Bus#start}
     *
     * @return возвращает время начала работы автобуса
     */
    public int getStart() {
        return start;
    }

    /**
     * Функция получения значения поля {@link Bus#end}
     *
     * @return возвращает время окончания работы автобуса
     */
    public int getEnd() {
        return end;
    }

    /**
     * Функция получения значения поля {@link Bus#driver}
     *
     * @return возвращает водителя автобуса
     */
    public Driver getDriver() {
        return driver;
    }
}
