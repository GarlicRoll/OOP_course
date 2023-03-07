package com.example.oop_course;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AppClass {

    public static EntityManager em;
    public static class CommandButtons extends JFrame {
        public CommandButtons() {
            super("CommandButtons");
            setSize(960, 540);
            setLocation(150, 100); // левый верхний угол окна
            setDefaultCloseOperation(EXIT_ON_CLOSE);
// создаем панель с табличным расположением для выравнивания размеров кнопок
            JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0)); // 1 строка, 2 столбца, промежутки 5 пикс. по гориз, 0 по верт.
// добавляем компоненты
            JButton button1 = new JButton("Водители");
            JButton button2 = new JButton("Автобусы");
            JButton button3 = new JButton("График");

            grid.add(button1);
            grid.add(button2);
            grid.add(button3);


// помещаем полученное в панель с последовательным расположением, выровненным по правому краю
            JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
            flow.add(grid);
// получаем панель содержимого
            Container c = getContentPane();
// помещаем строку кнопок вниз окна
            c.add(flow, BorderLayout.NORTH);
            info = new JTextArea("Пока событий не было\n"); // многострочное поле для вывода сообщений
            c.add(new JScrollPane(info));

            button1.addActionListener(new ActionL());
// выводим окно на экран
            setVisible(true);
        }
        JTextArea info;

        class ActionL implements ActionListener { // обработчик кнопки
            public void actionPerformed(ActionEvent e) {
                //info.append("Получено сообще.ние от кнопки- " + e.getActionCommand() + "\n");
                //for (int i = 1; i < 10; i++) {
                    //info.append(printDriver(em, i));
                //}
                List<Driver> drivers = em.createQuery("FROM Driver").getResultList();
                for (int i = 0; i < drivers.size(); i++) {
                    Driver dr = drivers.get(i);
                    int id = dr.getId();
                    String name = dr.getName();
                    int age = dr.getAge();
                    int experience = dr.getExperience();
                    String category = dr.getCategory();

                    String result;
                    result = "\n<**********>" +
                            "\nId: " + id +
                            "\nName: " + name +
                            "\nAge: " + age +
                            "\nExperience: " + experience +
                            "\nCategory " + category +
                            "\n<**********>";
                    info.append(result);
                }
            }
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
        em = emf.createEntityManager();

        System.out.println("Start a hibernate test");

        // Механизм сохранения целостности данных
        em.getTransaction().begin();

        Driver dr = (Driver) em.find(Driver.class, 1);
        // Привязка к бд
        em.persist(dr);
        // Сохранение в бд
        em.getTransaction().commit();

        new CommandButtons();

    }
}
