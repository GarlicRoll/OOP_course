package org.example.service;

import org.example.model.Route;

import java.util.List;
import java.util.Optional;

public interface RouteService {
    List<Route> findAllRoutes();
    Optional<Route> findRouteById(int id);
    Route saveRoute(Route route);
    List<Route> saveRouteList(List<Route> routes);
    Route updateRoute(Route route);
    void deleteRoute(int route);
}
