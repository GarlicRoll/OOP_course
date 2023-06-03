package org.example.model;

import org.hibernate.annotations.Type;

import java.util.List;
import javax.persistence.*;

/**
 * Финальный класс маршрута с полями buses, number, id, violations
 *
 * @author Пименов Глеб
 */

@Entity
@Table(name = "test.routes")
public class Route {
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
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Bus> buses;

    /**
     * Поле номер маршрута
     */
    @Column(name = "number")
    private int number;

    /**
     * Поле нарушения
     */
    //@OneToMany
    //@JoinColumn(name = "routeId")
    //@Column(columnDefinition = "text[]")
    //@Type(type = "org.example.model.CustomStringArrayType")
    @ElementCollection
    private List<String> violations;

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
    public List<String> getViolations() {
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
    public void setViolations(List<String> violations) {
        this.violations = violations;
    }

    /**
     * Функция установки значения поля {@link Route#buses}
     */
    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    @Override
    public String toString() {
        if (this.buses != null && this.violations != null) {
            return "Id: " + this.id + " Number: " + this.number + " Num of buses: " + this.buses.size() + " Num of violations: " + this.violations.size();
        } else {
            return "Id: " + this.id + " Number: " + this.number;
        }
    }
}
