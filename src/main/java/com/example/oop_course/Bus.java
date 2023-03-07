package com.example.oop_course;

import javax.persistence.*;

/**
 * Финальный класс автобуса с полями id, number, start, end и driver
 *
 * @author Пименов Глеб
 */
@Entity
@Table(name = "test.buses")
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
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    /**
     * Функция получения значения поля {@link Bus#number}
     *
     * @return возвращает номер автобуса
     */
    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    /**
     * Функция получения значения поля {@link Bus#start}
     *
     * @return возвращает время начала работы автобуса
     */
    @Column(name = "start")
    public int getStart() {
        return start;
    }

    /**
     * Функция получения значения поля {@link Bus#end}
     *
     * @return возвращает время окончания работы автобуса
     */
    @Column(name = "end")
    public int getEnd() {
        return end;
    }

    /**
     * Функция получения значения поля {@link Bus#driver}
     *
     * @return возвращает водителя автобуса
     */
    //@Column(name = "driver")
    @ManyToOne
    @JoinColumn(name = "driver")
    public Driver getDriver() {
        return driver;
    }

    /**
     * Функция установки значения поля {@link Bus#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Функция установки значения поля {@link Bus#number}
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Функция установки значения поля {@link Bus#driver}
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    /**
     * Функция установки значения поля {@link Bus#end}
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Функция установки значения поля {@link Bus#start}
     */
    public void setStart(int start) {
        this.start = start;
    }

}
