package com.example.oop_course;

/**
 * Финальный класс остановки, наследуемый от объекта, с полями x, y и name
 *
 * @author Пименов Глеб
 */
public final class Point extends Object {
    /**
     * Поле координата x
     */
    private int x;
    /**
     * Поле координата y
     */
    private int y;
    /**
     * Поле название остановки
     */
    private String name;

    /**
     * Функция получения значения поля {@link Point#x}
     *
     * @return возвращает координату x
     */
    public int getX() {
        return x;
    }

    /**
     * Функция получения значения поля {@link Point#y}
     *
     * @return возвращает координату y
     */
    public int getY() {
        return y;
    }

    /**
     * Функция получения значения поля {@link Point#name}
     *
     * @return возвращает название остановки
     */
    public String getName() {
        return name;
    }
}
