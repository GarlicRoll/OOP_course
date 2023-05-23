package org.example.service.impl;

import org.example.model.Bus;
import org.example.repository.BusRepository;
import org.example.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    BusRepository busRepository;

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
        Optional<Bus> busOptional = busRepository.findById(id);
        Bus bus;

        if (busOptional.isPresent()) {
            bus = busOptional.get();
            // Открепляем водителя, если он есть
            if (bus.getDriver() != null) {
                bus.setDriver(null);
                busRepository.save(bus);
            }
        }
        busRepository.deleteById(id);
    }
}
