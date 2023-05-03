package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@SpringBootApplication
public class Main {
    /**
     * Логирование
     */
    public static Logger logger = Logger.getLogger("TransportLog");

    private static void init() {
        FileHandler fh;
        try {
            fh = new FileHandler("transport.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info("Logger initialized");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception :: " , e);
        }
    }
    public static void main(String[] args) {
        init();

        SpringApplication.run(Main.class, args);
    }
}