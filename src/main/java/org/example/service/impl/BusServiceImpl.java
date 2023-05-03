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
        busRepository.deleteById(id);
    }
}
