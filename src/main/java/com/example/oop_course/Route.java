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
    private List<Bus> buses;

    /**
     * Поле номер маршрута
     */
    private int number;
    /**
     * Поле нарушения
     */
    private String violations;

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
    @OneToMany
    @JoinTable(name = "routebuses",
            joinColumns = @JoinColumn(name = "routeId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "busId", referencedColumnName = "id"))
    public List<Bus> getBuses() {
        return buses;
    }

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
    @OneToMany
    @JoinTable(name = "routeviolations",
            joinColumns = @JoinColumn(name = "routeId", referencedColumnName = "id"))
     */
    public String getViolations() {
        return violations;
    }

    /**
     * Функция установки значения поля {@link Route#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Функция установки значения поля {@link Route#number}
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Функция установки значения поля {@link Route#violations}
     */
    public void setViolations(String violations) {
        this.violations = violations;
    }

    /**
     * Функция установки значения поля {@link Route#buses}
     */
    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }
}
