package org.example.service.impl;

import org.example.model.Route;
import org.example.repository.RouteRepository;
import org.example.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PreRemove;
import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public RouteServiceImpl() {

    }

    @Override
    public List<Route> findAllRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public Optional<Route> findRouteById(int id) {
        return routeRepository.findById(id);
    }

    @Override
    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }

    @Override
    public List<Route> saveRouteList(List<Route> routes) {
        return routeRepository.saveAll(routes);
    }

    @Override
    public Route updateRoute(Route route) {
        return routeRepository.save(route);
    }

    @Override
    public void deleteRoute(int id) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        Route route;

        if (routeOptional.isPresent()) {
            route = routeOptional.get();
            // Открепляем водителя, если он есть
            if (route.getBuses() != null) {
                route.setBuses(null);
                routeRepository.save(route);
            }
        }
        routeRepository.deleteById(id);
    }
}
