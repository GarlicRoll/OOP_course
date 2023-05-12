package org.example.controller;


import  org.example.Main;
import  org.example.exceptions.NotFoundException;
import org.example.model.Bus;
import org.example.model.Driver;
import  org.example.model.Route;
import org.example.model.Violation;
import org.example.service.BusService;
import org.example.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

/***
 * Rest-контроллер для сущности маршрута
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("api/v1/route")
public class RouteController {

    @Autowired
    RouteService routeService;

    @Autowired
    BusService busService;

    /***
     * Получение всех маршрутов
     * @return Список маршрутов в виде экзмеляров класса Route
     */
    @GetMapping
    public List<Route> list() {
        List<Route> routes = routeService.findAllRoutes();

        Main.logger.log(Level.INFO, "201 OK Server. Read all routes.");

        return routes;
    }

    /***
     * Получение одного маршрута
     * @param id Уникальный номер маршрута, которого необходимо найти
     * @return Маршрут в виде экземпляра класса Route
     */
    @GetMapping("{id}")
    public Optional<Route> read(@PathVariable int id) {

        Optional<Route> route = routeService.findRouteById(id);

        if (!route.isPresent()) {
            Main.logger.log(Level.WARNING, "404 Server Error. Not found ID: " + id);
            throw new NotFoundException();
        }

        Main.logger.log(Level.INFO, "201 OK Server. Read one route. " + route);

        return route;
    }

    /***
     * Добавление одного маршрута
     * @param route Маршрут, который будет добавлен в виде экземпляра класса Route
     * @return Добавленный маршрут и HTTP код
     */
    @PostMapping
    public ResponseEntity<Route> create(@RequestBody Route route) {

        route = routeService.saveRoute(route);

        Main.logger.log(Level.INFO, "201 OK Server. Post one route. " + route.toString());

        return new ResponseEntity<>(route, HttpStatus.valueOf(201));
    }

    /***
     * Добавление списка маршрутов
     * @param routes Список маршрутов, которые будут добавлены в виде объектов Route
     * @return Список добавленных маршрутов и HTTP код
     */
    @PostMapping("list")
    public ResponseEntity<List<Route>> createList(@RequestBody List<Route> routes) {

        routes = routeService.saveRouteList(routes);

        Main.logger.log(Level.INFO, "201 OK Server. Post list of routes. ");

        return new ResponseEntity<>(routes, HttpStatus.valueOf(201));
    }

    /***
     * Обновление маршрута
     * @param id Уникальный номер маршрута, которого обновляют
     * @param route Объект Route (новые поля)
     * @return Обновлённый маршрут и HTTP код
     */
    @PatchMapping("{id}")
    public ResponseEntity<Route> update(@PathVariable int id, @RequestBody Route route) {

        Route routeNew = null;
        Optional<Route> routeOptional = routeService.findRouteById(id);
        if (routeOptional.isPresent()) {
            routeNew = routeOptional.get();
            routeNew.setNumber(route.getNumber());
            route = routeService.updateRoute(routeNew);
        } else {
            throw new NotFoundException();
        }

        Main.logger.log(Level.INFO, "201 OK Server. Update one route. " + route.toString());

        return new ResponseEntity<>(route, HttpStatus.valueOf(201));
    }

    /***
     * Добавление автобуса на маршрут
     * @param id Уникальный номер маршрута, которого обновляют
     * @param bus Объект Bus (Новый автобус)
     * @return Обновлённый маршрут и HTTP код
     */
    @PatchMapping("addBusTo/{id}")
    public ResponseEntity<Route> addBus(@PathVariable int id, @RequestBody Bus bus) {

        int code = 201;
        Route route = null;
        Optional<Route> routeOptional = routeService.findRouteById(id);
        if (routeOptional.isPresent()) {
            route = routeOptional.get();
            List<Bus> buses = route.getBuses();
            boolean alreadyThere = false;
            for (Bus value : buses) {
                if (value.getId() == bus.getId()) {
                    alreadyThere = true;
                    code = 409;
                    break;
                }
            }
            if (!alreadyThere) {
                buses.add(bus);
            }
            route.setBuses(buses);
            route = routeService.updateRoute(route);
        } else {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(route, HttpStatus.valueOf(code));
    }

    /***
     * Добавление нарушения на маршрут
     * @param id Уникальный номер маршрута, которого обновляют
     * @param violation Объект Violation (Новое нарушение)
     * @return Обновлённый маршрут и HTTP код
     */
    @PatchMapping("addViolationTo/{id}")
    public ResponseEntity<Route> addViolation(@PathVariable int id, @RequestBody Map<String, String> violation) {
        Route route = null;
        System.out.println("HILE" + violation.get("violation"));
        Optional<Route> routeOptional = routeService.findRouteById(id);
        if (routeOptional.isPresent()) {
            route = routeOptional.get();
            List<String> violations = route.getViolations();
            violations.add(violation.get("violation"));
            route.setViolations(violations);
            route = routeService.updateRoute(route);
        } else {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(route, HttpStatus.valueOf(201));
    }

    @PatchMapping("removeViolationsFrom/{id}")
    public ResponseEntity<Route> removeViolations(@PathVariable int id) {
        Route route = null;
        Optional<Route> routeOptional = routeService.findRouteById(id);
        if (routeOptional.isPresent()) {
            route = routeOptional.get();
            route.setViolations(null);
            route = routeService.updateRoute(route);
        } else {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(route, HttpStatus.valueOf(201));
    }

    /***
     * Удаление автобуса с маршрута
     * @param id Уникальный номер маршрута, которого обновляют
     * @param bus Объект Bus (Удаляемый автобус)
     * @return Обновлённый маршрут и HTTP код
     */
    @PatchMapping("removeBusFrom/{id}")
    public ResponseEntity<Route> removeBus(@PathVariable int id, @RequestBody Bus bus) {

        Route route = null;
        Optional<Route> routeOptional = routeService.findRouteById(id);
        if (routeOptional.isPresent()) {
            route = routeOptional.get();
            List<Bus> buses = route.getBuses();
            int busId = -1;
            for (int i = 0; i < buses.size(); i++) {
                if (buses.get(i).getId() == bus.getId()) {
                    busId = i;
                }
            }
            if (busId != -1) {
                buses.remove(busId);
            } else {
                throw new NotFoundException();
            }
            route.setBuses(buses);
            route = routeService.updateRoute(route);
        } else {
            throw new NotFoundException();
        }


        return new ResponseEntity<>(route, HttpStatus.valueOf(201));
    }

    /***
     * Удаление маршрута
     * @param id Уникальный номер маршрута, которго удалят
     * @return Уникальный номер маршрута и HTTP код
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Integer> delete(@PathVariable int id) {

        routeService.findRouteById(id);

        routeService.deleteRoute(id);

        Main.logger.log(Level.INFO, "201 OK Server. Delete one route. " + Integer.toString(id));

        return new ResponseEntity<>(id, HttpStatus.valueOf(201));
    }
}
