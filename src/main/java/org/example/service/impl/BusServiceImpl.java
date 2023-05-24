package org.example.service.impl;

import org.example.model.Bus;
import org.example.model.Driver;
import org.example.model.Route;
import org.example.repository.BusRepository;
import org.example.repository.DriverRepository;
import org.example.repository.RouteRepository;
import org.example.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    BusRepository busRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    RouteRepository routeRepository;

    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public BusServiceImpl() {

    }

    @Override
    public List<Bus> findAllBuses() {
        return busRepository.findAll();
    }

    @Override
    public Optional<Bus> findBusById(int id) {
        return busRepository.findById(id);
    }

    @Override
    public Bus saveBus(Bus bus) {
        return busRepository.save(bus);
    }

    @Override
    public List<Bus> saveBusList(List<Bus> buses) {
        return busRepository.saveAll(buses);
    }

    @Override
    public Bus updateBus(Bus bus) {
        return busRepository.save(bus);
    }

    @Override
    public void deleteBus(int id) {
        List<Driver> drivers = driverRepository.findAll();
        Optional<Bus> busOptional = busRepository.findById(id);
        List<Route> routes = routeRepository.findAll();
        Bus bus;

        if (busOptional.isPresent()) {
            bus = busOptional.get();
            for (Driver driver: drivers) {
                // Открепляем водителя, если он есть
                if (bus.getDriver() != null) {
                    if (bus.getDriver().getId() == driver.getId()) {
                        bus.setDriver(null);
                        driver.setBus(null);
                        driverRepository.save(driver);
                        busRepository.save(bus);
                        break;
                    }
                }
            }
            List<Integer> busInRouteIds = new ArrayList<>();
            for (Route route : routes) {
                // Открепляем автобус от маршрута, если прикреплён
                for (int busInRouteId = 0; busInRouteId < route.getBuses().size(); busInRouteId++) {
                    if (route.getBuses().get(busInRouteId).getId() == id) {
                        busInRouteIds.add(busInRouteId);
                    }
                }
                for (int busInRoute: busInRouteIds) {
                    route.getBuses().remove(busInRoute);
                }
            }
            routeRepository.saveAll(routes);
        }
        busRepository.deleteById(id);
    }
}
