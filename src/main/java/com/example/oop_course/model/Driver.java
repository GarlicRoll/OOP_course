package com.example.oop_course.model;

import javax.persistence.*;

/**
 * Финальный класс водителя с полями id, name, age, experience и category
 *
 * @author Пименов Глеб
 */
@Entity
@Table(name = "test.drivers")
public final class Driver {
    /**
     * Поле id
     */
    private int id;
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

    Driver() {

    }

    public Driver(String name, String category) {
        this.name = name;
        this.category = category;
    }

    /**
     * Функция получения значения поля {@link Driver#id}
     *
     * @return возвращает id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    /**
     * Функция получения значения поля {@link Driver#name}
     *
     * @return возвращает имя водителя
     */
    @Column(name = "name")
    public String getName() {
        System.out.println(name);
        return name;
    }

    /**
     * Функция получения значения поля {@link Driver#age}
     *
     * @return возвращает возраст водителя
     */
    @Column(name = "age")
    public int getAge() {
        return age;
    }

    /**
     * Функция получения значения поля {@link Driver#experience}
     *
     * @return возвращает опыт водителя автобуса
     */
    @Column(name = "experience")
    public int getExperience() {
        return experience;
    }

    /**
     * Функция получения значения поля {@link Driver#category}
     *
     * @return возвращает категорию водителя автобуса
     */
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    /**
     * Функция установки значения поля {@link Driver#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Функция установки значения поля {@link Driver#name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Функция установки значения поля {@link Driver#category}
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Функция установки значения поля {@link Driver#experience}
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * Функция установки значения поля {@link Driver#age}
     */
    public void setAge(int age) {
        this.age = age;
    }
}
