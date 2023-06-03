package org.example.service;


import org.example.model.Route;
import org.example.repository.RouteRepository;
import org.example.service.impl.DriverServiceImpl;
import org.example.service.impl.RouteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.model.Driver;
import org.example.repository.DriverRepository;

import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса (логика) маршрута
 */
@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    /**
     * Пример уникального идентификатора
     */
    private static final long ID = 1L;

    /**
     * Хранилище родителя (интерфейс для работы с базой данных)
     */
    @Mock
    private RouteRepository eventRepository;

    /**
     * Рализация сервиса (логики) маршрута
     */
    @InjectMocks
    private RouteServiceImpl service;

    /**
     * Тест сохранения
     */
    @Test
    void saveRoute_shouldCallRepository() {
        Route route = mock(Route.class);

        service.saveRoute(route);

        verify(eventRepository).save(route);
    }
}

