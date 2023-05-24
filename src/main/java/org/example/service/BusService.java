package org.example.service;

import org.example.model.Bus;

import java.util.List;
import java.util.Optional;

public interface BusService {

    List<Bus> findAllBuses();
    Optional<Bus> findBusById(int id);
    Bus saveBus(Bus bus);
    List<Bus> saveBusList(List<Bus> buses);
    Bus updateBus(Bus bus);
    void deleteBus(int bus);
    Bus clearDriver(int bus);

}
