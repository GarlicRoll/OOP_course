package org.example.controller;

import org.example.Main;
import org.example.exceptions.NotFoundException;
import org.example.model.Bus;
import org.example.service.BusService;
import org.example.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/***
 * Rest-контроллер для сущности автобуса
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("api/v1/bus")
public class BusController {

    @Autowired
    BusService busService;

    @Autowired
    DriverService driverService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    /***
     * Получение всех автобусов
     * @return Список автобусов в виде экзмеляров класса Bus
     */
    @GetMapping
    public List<Bus> list() {

        List<Bus> buses = busService.findAllBuses();

        Main.logger.log(Level.INFO, "201 OK Server. Read all buses.");

        return buses;
    }

    /***
     * Получение одного автобуса
     * @param id Уникальный номер автобуса, которого необходимо найти
     * @return Автобус в виде экземпляра класса Bus
     */
    @GetMapping("{id}")
    public Optional<Bus> read(@PathVariable int id) {

        Optional<Bus> bus = busService.findBusById(id);

        if (!bus.isPresent()) {
            Main.logger.log(Level.WARNING, "404 Server Error. Not found ID: " + id);
            throw new NotFoundException();
        }

        Main.logger.log(Level.INFO, "201 OK Server. Read one bus. " + bus);

        return bus;
    }

    /***
     * Добавление одного автобуса
     * @param bus Автобус, который будет добавлен в виде экземпляра класса Bus
     * @return Добавленный автобус и HTTP код
     */
    @PostMapping
    public ResponseEntity<Bus> create(@RequestBody Bus bus) {

        List<Bus> buses = busService.findAllBuses();

        // Проверка того, прикреплён ли водитель к какому-либо ещё автобусу
        boolean alreadyExists = false;
        boolean alreadyExistsNumber = false;
        if (bus.getDriver() != null) {
            for (Bus value : buses) {
                if (value.getDriver() != null && (value.getDriver().getId() == bus.getDriver().getId())) {
                    alreadyExists = true;
                    break;
                }
            }
        }

        for (Bus busExtra: buses) {
            if (busExtra.getNumber() == bus.getNumber() && bus.getId() != busExtra.getId()) {
                alreadyExistsNumber = true;
                break;
            }
        }

        int statusCode = 201;
        if (!alreadyExistsNumber) {
            if (!alreadyExists) {
                bus = busService.saveBus(bus);
                Main.logger.log(Level.INFO, "201 OK Server. Post one bus. " + bus);
            } else {
                Main.logger.log(Level.INFO, "409 Server. Driver already connected to another bus. " + bus);
                statusCode = 409;
            }
        } else {
            Main.logger.log(Level.INFO, "409 (1) Server. This number already exists. " + bus);
            statusCode = 409;
        }


        return new ResponseEntity<>(bus, HttpStatus.valueOf(statusCode));
    }

    /***
     * Добавление списка автобусов
     * @param buses Список автобусов, которые будут добавлены в виде объектов Bus
     * @return Список добавленных автобусов и HTTP код
     */
    @PostMapping("list")
    public ResponseEntity<List<Bus>> createList(@RequestBody List<Bus> buses) {

        buses = busService.saveBusList(buses);

        Main.logger.log(Level.INFO, "201 OK Server. Post list of buses. ");

        return new ResponseEntity<>(buses, HttpStatus.valueOf(201));
    }

    /***
     * Обновление автобуса
     * @param id Уникальный номер автобуса, которого обновляют
     * @param bus Объект Bus (новые поля)
     * @return Обновлённый автобус и HTTP код
     */
    @PatchMapping ("{id}")
    public ResponseEntity<Bus> update(@PathVariable int id, @RequestBody Bus bus) {

        bus.setId(id);

        List<Bus> buses = busService.findAllBuses();

        // Проверка того, прикреплён ли водитель к какому-либо ещё автобусу
        boolean alreadyExists = false;
        boolean alreadyExistsNumber = false;
        if (bus.getDriver() != null) {
            for (Bus value : buses) {
                if (value.getDriver() != null && (value.getDriver().getId() == bus.getDriver().getId())) {
                    if (value.getId() != bus.getId()) {
                        alreadyExists = true;
                        break;
                    }
                }
            }
        }

        int statusCode = 201;

        for (Bus busExtra: buses) {
            if (busExtra.getNumber() == bus.getNumber() && bus.getId() != busExtra.getId()) {
                alreadyExistsNumber = true;
                break;
            }
        }

        if (!alreadyExistsNumber) {
            if (!alreadyExists) {
                bus = busService.saveBus(bus);
                Main.logger.log(Level.INFO, "201 OK Server. Update one bus. " + bus);
            } else {
                Main.logger.log(Level.INFO, "409 Server. Driver already connected to another bus. " + bus);
                statusCode = 409;
            }
        } else {
            Main.logger.log(Level.INFO, "409 (1) Server. This number already exists. " + bus);
            statusCode = 409;
        }

        return new ResponseEntity<>(bus, HttpStatus.valueOf(statusCode));
    }

    /***
     * Удаление автобуса
     * @param id Уникальный номер автобуса, которго удалят
     * @return Уникальный номер автобуса и HTTP код
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Integer> delete(@PathVariable int id) {

        read(id);

        busService.deleteBus(id);

        Main.logger.log(Level.INFO, "201 OK Server. Delete one bus. " + id);

        return new ResponseEntity<>(id, HttpStatus.valueOf(201));
    }
}
