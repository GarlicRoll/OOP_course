package com.example.oop_course;

/**
 * Абстрактный класс объекта с id
 *
 * @author Пименов Глеб
 */
public abstract class Object {
    /**
     * Поле id
     */
    int id;

    /**
     * Функция получения значения поля {@link Object#id}
     *
     * @return возвращает id
     */
    public int getId() {
        return id;
    }
}
