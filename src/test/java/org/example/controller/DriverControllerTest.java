package org.example.controller;

import org.example.model.Driver;
import org.example.service.DriverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * Тестирование контроллера водителя
 */
@ExtendWith(MockitoExtension.class)
public class DriverControllerTest {

    /**
     * Пример уникального идентификатора
     */
    private final static long ID = 1L;

    /**
     * Сервис (логика) водителя
     */
    @Mock
    private DriverService driverService;

    /**
     * Контроллер водителя
     */
    @InjectMocks
    private DriverController driverController;

    /**
     * Тест сохранения
     */
    @Test
    void saveDriver_shouldCallService() {
        Driver driver = mock(Driver.class);

        driverController.create(driver);

        verify(driverService).saveDriver(driver);

    }
}
