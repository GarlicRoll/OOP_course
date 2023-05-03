package org.example.service;

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
 * Тестирование сервиса (логика) водителя
 */
@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    /**
     * Пример уникального идентификатора
     */
    private static final long ID = 1L;

    /**
     * Хранилище родителя (интерфейс для работы с базой данных)
     */
    @Mock
    private DriverRepository eventRepository;

    /**
     * Рализация сервиса (логики) водителя
     */
    @InjectMocks
    private DriverServiceImpl service;

    /**
     * Тест сохранения
     */
    @Test
    void saveDriver_shouldCallRepository() {
        Driver driver = mock(Driver.class);

        service.saveDriver(driver);

        verify(eventRepository).save(driver);
    }
}
