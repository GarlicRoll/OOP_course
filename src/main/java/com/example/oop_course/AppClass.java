package com.example.oop_course;

import javax.persistence.*;
import java.util.List;
import com.example.oop_course.Driver;

public class AppClass {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
        EntityManager em = emf.createEntityManager();

        System.out.println("Start a hibernate test");

        // Механизм сохранения целостности данных
        em.getTransaction().begin();

        Driver dr = new Driver();

        // Привязка к бд
        em.persist(dr);
        // Сохранение в бд
        em.getTransaction().commit();

        System.out.println("New driver ID is " + dr.getId());

    }
}
