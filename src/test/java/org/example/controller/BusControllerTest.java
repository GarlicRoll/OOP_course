package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.model.Bus;
import org.example.service.BusService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BusControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BusService busService;
    @Autowired
    private BusController busController;

    @Test
    void list_shouldOk() throws Exception {
        this.mockMvc.perform(get("/api/v1/bus/")).andExpect(status().isOk());
    }

    @Test
    void read_shouldNotFind() throws Exception {
        this.mockMvc.perform(get("/api/v1/bus/9999999")).andExpect(status().is(404));
    }

    @Test
    void create_shouldOk() throws Exception {
        Bus bus = new Bus();
        bus.setStart(12);
        bus.setEnd(20);
        bus.setNumber(777);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(bus);

        this.mockMvc.perform(post("/api/v1/bus/").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201));
    }

    @Test
    void createList_shouldOk() throws Exception {
        Bus bus = new Bus();
        bus.setStart(12);
        bus.setEnd(20);
        bus.setNumber(777);

        List<Bus> buses = new ArrayList<>();
        buses.add(bus);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(buses);

        this.mockMvc.perform(post("/api/v1/bus/list").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201));
    }

    @Test
    void update_shouldOk() throws Exception {
        Bus bus = new Bus();
        bus.setId(3);
        bus.setStart(12);
        bus.setEnd(20);
        bus.setNumber(777);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(bus);

        this.mockMvc.perform(patch("/api/v1/bus/3").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201));
    }

    @Test
    void delete_shouldNotFind() throws Exception {
        this.mockMvc.perform(delete("/api/v1/bus/999999")).andExpect(status().is(404));
    }
}