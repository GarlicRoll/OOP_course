package org.example.model;

import javax.persistence.*;

/**
 * Финальный класс водителя с полями id, name, age, experience и category
 *
 * @author Пименов Глеб
 */
@Entity
@Table(name = "test.drivers")
public class Driver {
    /**
     * Поле id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Поле имя водителя
     */
    @Column(name = "name")
    private String name;
    /**
     * Поле возраст водителя
     */
    @Column(name = "age")
    private int age;
    /**
     * Поле опыт вождения водителя
     */
    @Column(name = "experience")
    private int experience;
    /**
     * Поле категория водителя
     */
    @Column(name = "category")
    private String category;

    /**
     * Поле автобуса (для связи one-to-one)
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="driver")
    private Bus bus;

    public Driver() {

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
    public int getId() {
        return id;
    }

    /**
     * Функция получения значения поля {@link Driver#name}
     *
     * @return возвращает имя водителя
     */
    public String getName() {
        //System.out.println(name);
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

    @Override
    public String toString() {
        return "Id: " + this.id + " Name: " + this.name + " Age: " + this.age + " Category: " + this.category;
    }

    /*
    /**
     * Функция получения значения поля {@link Driver#bus}
     *
     * @return возвращает автобус водителя

    public Bus getBus() {
        return bus;
    }
    */

    /*
    /**
     * Функция установки значения поля {@link Driver#bus}

    public void setBus(Bus bus) {
        this.bus = bus;
    }
    */
}
