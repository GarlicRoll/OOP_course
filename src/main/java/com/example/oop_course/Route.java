package com.example.oop_course;

import java.util.List;
import javax.persistence.*;

/**
 * Финальный класс маршрута с полями buses, number, id, violations
 *
 * @author Пименов Глеб
 */

@Entity
@Table(name = "test.routes")
public final class Route {
    /**
     * Поле id
     */
    private int id;
    /**
     * Поле автобусы
     */
    /*
    private int buses;

     */
    /**
     * Поле номер маршрута
     */
    private int number;
    /**
     * Поле нарушения
     */
    /*
    private String violations;
    */

    /**
     * Функция получения значения поля {@link Route#number}
     *
     * @return возвращает номер маршрута
     */
    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    /**
     * Функция получения значения поля {@link Route#buses}
     *
     * @return возвращает список автобусов на маршруте
     */
    /*
    @Column(name = "buses")
    public int getBuses() {
        return buses;
    }
     */

    /**
     * Функция получения значения поля {@link Route#id}
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
     * Функция получения значения поля {@link Route#violations}
     *
     * @return возвращает нарушения
     */
    /*
    @Column(name = "violations")
    public String getViolations() {
        return violations;
    }
    */
    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    /*
    public void setViolations(String violations) {
        this.violations = violations;
    }
    */
    /*
    public void setBuses(int buses) {
        this.buses = buses;
    }

     */
}
