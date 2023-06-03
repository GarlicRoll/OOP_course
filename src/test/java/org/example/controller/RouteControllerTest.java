package org.example.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.model.Driver;
import org.example.model.Route;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RouteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DriverController driverController;

    @Test
    void list_shouldOk() throws Exception {
        this.mockMvc.perform(get("/api/v1/route/")).andExpect(status().isOk());
    }

    @Test
    void read_shouldNotFind() throws Exception {
        this.mockMvc.perform(get("/api/v1/route/9999999")).andExpect(status().is(404));
    }

    @Test
    void create_shouldOk() throws Exception {
        Route route = new Route();
        route.setNumber(8000);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(route);

        this.mockMvc.perform(post("/api/v1/route/").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201));
    }

    @Test
    void createList_shouldOk() throws Exception {
        Route route = new Route();
        route.setNumber(8000);

        List<Route> routes = new ArrayList<>();
        routes.add(route);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(routes);

        this.mockMvc.perform(post("/api/v1/route/list").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201));
    }

    @Test
    void update_shouldOk() throws Exception {
        Route route = new Route();
        route.setNumber(8000);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(route);

        this.mockMvc.perform(patch("/api/v1/route/3").content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201));
    }

    @Test
    void delete_shouldNotFind() throws Exception {
        this.mockMvc.perform(delete("/api/v1/route/999999")).andExpect(status().is(404));
    }
}