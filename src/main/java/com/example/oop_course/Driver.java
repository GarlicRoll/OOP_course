package com.example.oop_course;

/**
 * Финальный класс водителя, наследуемый от объекта, с полями name, age, experience и category
 *
 * @author Пименов Глеб
 */
public final class Driver extends Object {
    /**
     * Поле имя водителя
     */
    private String name;
    /**
     * Поле возраст водителя
     */
    private int age;
    /**
     * Поле опыт вождения водителя
     */
    private int experience;
    /**
     * Поле категория водителя
     */
    private String category;

    /**
     * Функция получения значения поля {@link Driver#name}
     *
     * @return возвращает имя водителя
     */
    public String getName() {
        return name;
    }

    /**
     * Функция получения значения поля {@link Driver#age}
     *
     * @return возвращает возраст водителя
     */
    public int getAge() {
        return age;
    }

    /**
     * Функция получения значения поля {@link Driver#experience}
     *
     * @return возвращает опыт водителя автобуса
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Функция получения значения поля {@link Driver#category}
     *
     * @return возвращает категорию водителя автобуса
     */
    public String getCategory() {
        return category;
    }
}
