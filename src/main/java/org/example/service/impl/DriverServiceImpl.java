package org.example.service.impl;

import org.example.model.Bus;
import org.example.model.Driver;
import org.example.repository.BusRepository;
import org.example.repository.DriverRepository;
import org.example.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PreRemove;
import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    BusRepository busRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public DriverServiceImpl() {

    }

    @Override
    public List<Driver> findAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public Optional<Driver> findDriverById(int id) {
        return driverRepository.findById(id);
    }

    @Override
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public List<Driver> saveDriverList(List<Driver> drivers) {
        return driverRepository.saveAll(drivers);
    }

    @Override
    public Driver updateDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public void deleteDriver(int id) {
        List<Bus> buses = busRepository.findAll();

        for (Bus bus : buses) {
            if (bus.getDriver() != null) {
                if (bus.getDriver().getId() == id) {
                    bus.setDriver(null);
                    busRepository.save(bus);
                    break;
                }
            }
        }
        driverRepository.deleteById(id);
    }
}
