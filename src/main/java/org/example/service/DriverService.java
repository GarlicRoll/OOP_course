package org.example.service;
import org.example.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    List<Driver> findAllDrivers();
    Optional<Driver> findDriverById(int id);
    Driver saveDriver(Driver driver);
    List<Driver> saveDriverList(List<Driver> drivers);
    Driver updateDriver(Driver driver);
    void deleteDriver(int driver);

}
