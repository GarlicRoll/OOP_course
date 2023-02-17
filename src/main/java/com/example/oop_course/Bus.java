package com.example.oop_course;

/**
 * Финальный класс автобуса, наследуемый от объекта, с полями number и driver
 *
 * @author Пименов Глеб
 */
public final class Bus extends Object {
    /**
     * Поле номера автобуса
     */
    private int number;
    /**
     * Поле водителя автобуса
     */
    private Driver driver;

    /**
     * Функция получения значения поля {@link Bus#number}
     *
     * @return возвращает номер автобуса
     */
    public int getNumber() {
        return number;
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
