package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "test.routeViolations")
public class Violation {

    @Id
    @GeneratedValue
    private int id;

    private String violation;

    public Violation() {
    }

    public Violation(int id, String violation) {
        this.id = id;
        this.violation = violation;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
