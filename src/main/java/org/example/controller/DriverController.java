package org.example.controller;

import org.example.Main;
import org.example.model.Driver;
import org.example.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/***
 * Rest-контроллер для сущности водителя
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("api/v1/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /***
     * Получение всех водителей
     * @return Список водителей в виде экзмеляров класса Driver
     */
    @GetMapping
    public List<Driver> list() {

        List<Driver> drivers = driverService.findAllDrivers();

        Main.logger.log(Level.INFO, "201 OK Server. Read all drivers.");

        return drivers;
    }

    /***
     * Получение одного водителя
     * @param id Уникальный номер водителя, которого необходимо найти
     * @return Водитель в виде экземпляра класса Driver
     */
    @GetMapping("{id}")
    public Optional<Driver> read(@PathVariable int id) {

        Optional<Driver> driver = driverService.findDriverById(id);

        if (!driver.isPresent()) {
            Main.logger.log(Level.WARNING, "404 Server Error. Not found ID: " + id);
            throw new NotFoundException();
        }

        Main.logger.log(Level.INFO, "201 OK Server. Read one driver. " + driver);

        return driver;
    }

    /***
     * Добавление одного водителя
     * @param driver Водитель, который будет добавлен в виде экземпляра класса Driver
     * @return Добавленный водитель и HTTP код
     */
    @PostMapping
    public ResponseEntity<Driver> create(@RequestBody Driver driver) {

        driver = driverService.saveDriver(driver);

        Main.logger.log(Level.INFO, "201 OK Server. Post one driver. " + driver);

        return new ResponseEntity<>(driver, HttpStatus.valueOf(201));
    }

    /***
     * Добавление списка водителей
     * @param drivers Список водителей, которые будут добавлены в виде объектов Driver
     * @return Список добавленных водителей и HTTP код
     */
    @PostMapping("list")
    public ResponseEntity<List<Driver>> createList(@RequestBody List<Driver> drivers) {

        drivers = driverService.saveDriverList(drivers);

        Main.logger.log(Level.INFO, "201 OK Server. Post list of drivers. ");

        return new ResponseEntity<>(drivers, HttpStatus.valueOf(201));
    }

    /***
     * Обновление водителя
     * @param id Уникальный номер водителя, которого обновляют
     * @param driver Объект Driver (новые поля)
     * @return Обновлённый водитель и HTTP код
     */
    @PatchMapping("{id}")
    public ResponseEntity<Driver> update(@PathVariable int id, @RequestBody Driver driver) {

        driver.setId(id);

        driver = driverService.updateDriver(driver);

        Main.logger.log(Level.INFO, "201 OK Server. Update one driver. " + driver);

        return new ResponseEntity<>(driver, HttpStatus.valueOf(201));
    }

    /***
     * Удаление водителя
     * @param id Уникальный номер водителя, которго удалят
     * @return Уникальный номер водителя и HTTP код
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Integer> delete(@PathVariable int id) {

        read(id);

        driverService.deleteDriver(id);

        Main.logger.log(Level.INFO, "201 OK Server. Delete one driver. " + id);

        return new ResponseEntity<>(id, HttpStatus.valueOf(201));
    }
}
