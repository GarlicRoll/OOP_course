package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.model.Bus;
import org.example.model.Driver;
import org.example.service.DriverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тестирование контроллера водителя
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
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


    @Autowired
    private MockMvc mockMvc;


    /**
     * Тест сохранения
     */
    @Test
    void saveDriver_shouldCallService() {
        Driver driver = mock(Driver.class);

        driverController.create(driver);

        verify(driverService).saveDriver(driver);

    }

    @Test
    void list_shouldOk() throws Exception {
        this.mockMvc.perform(get("/api/v1/driver/")).andExpect(status().isOk());
    }

    @Test
    void read_shouldNotFind() throws Exception {
        this.mockMvc.perform(get("/api/v1/driver/9999999")).andExpect(status().is(404));
    }

    @Test
    void create_shouldOk() throws Exception {
        Driver driver = new Driver();
        driver.setAge(40);
        driver.setCategory("D");
        driver.setExperience(3);
        driver.setName("Gleb");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(driver);

        this.mockMvc.perform(post("/api/v1/driver/").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201));
    }

    @Test
    void createList_shouldOk() throws Exception {
        Driver driver = new Driver();
        driver.setAge(40);
        driver.setCategory("D");
        driver.setExperience(3);
        driver.setName("Gleb");

        List<Driver> drivers = new ArrayList<>();
        drivers.add(driver);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(drivers);

        this.mockMvc.perform(post("/api/v1/driver/list").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201));
    }

    @Test
    void update_shouldOk() throws Exception {
        Driver driver = new Driver();
        driver.setAge(40);
        driver.setCategory("D");
        driver.setExperience(3);
        driver.setName("Gleb");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(driver);

        this.mockMvc.perform(patch("/api/v1/driver/3").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201));
    }

    @Test
    void delete_shouldNotFind() throws Exception {
        this.mockMvc.perform(delete("/api/v1/driver/999999")).andExpect(status().is(404));
    }
}
