package org.example.service;


import org.example.model.Bus;
import org.example.repository.BusRepository;
import org.example.service.impl.BusServiceImpl;
import org.example.service.impl.DriverServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.model.Driver;
import org.example.repository.DriverRepository;

import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса (логика) автобуса
 */
@ExtendWith(MockitoExtension.class)
class BusServiceTest {

    /**
     * Пример уникального идентификатора
     */
    private static final long ID = 1L;

    /**
     * Хранилище родителя (интерфейс для работы с базой данных)
     */
    @Mock
    private BusRepository eventRepository;

    /**
     * Рализация сервиса (логики) автобуса
     */
    @InjectMocks
    private BusServiceImpl service;

    /**
     * Тест сохранения
     */
    @Test
    void saveBus_shouldCallRepository() {
        Bus bus = mock(Bus.class);

        service.saveBus(bus);

        verify(eventRepository).save(bus);
    }
}
