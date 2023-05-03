package org.example.model;

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
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Поле автобусы
     */
    //@JoinTable(name = "routebuses",
    //            joinColumns = @JoinColumn(name = "routeId", referencedColumnName = "id"),
    //            inverseJoinColumns = @JoinColumn(name = "busId", referencedColumnName = "id"))
    @ManyToMany
    private List<Bus> buses;

    /**
     * Поле номер маршрута
     */
    @Column(name = "number")
    private int number;

    /**
     * Поле нарушения
     */
    @OneToMany
    @JoinColumn(name = "routeId")
    private List<Violation> violations;

    /**
     * Функция получения значения поля {@link Route#number}
     *
     * @return возвращает номер маршрута
     */
    public int getNumber() {
        return number;
    }

    /**
     * Функция получения значения поля {@link Route#buses}
     *
     * @return возвращает список автобусов на маршруте
     */
    public List<Bus> getBuses() {
        return buses;
    }

    /**
     * Функция получения значения поля {@link Route#id}
     *
     * @return возвращает id
     */
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
    public List<Violation> getViolations() {
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
    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    /**
     * Функция установки значения поля {@link Route#buses}
     */
    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }
}