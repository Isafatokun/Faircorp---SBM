package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.model.Building;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuildingController.class)
class BuildingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WindowDao windowDao;

    @MockBean
    private RoomDao roomDao;

    @MockBean
    private BuildingDao buildingDao;

    @MockBean
    private HeaterDao heaterDao;

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    void shouldReturnBuildingById() throws Exception {
        Building building = createBuilding("Building 1");

        given(buildingDao.findById(1L)).willReturn(Optional.of(building));

        mockMvc.perform(get("/api/buildings/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(building.getId()))
                .andExpect(jsonPath("$.name").value(building.getName()));
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    void shouldLoadABuildingAndReturnNullIfNotFound() throws Exception {
        given(buildingDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/buildings/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    void shouldGetAllHeaters() throws Exception {
        List<Building> buildings = List.of(
                createBuilding("Building 1"),
                createBuilding("Building 2")
        );

        given(buildingDao.findAll()).willReturn(buildings);

        mockMvc.perform(get("/api/buildings").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("Building 1","Building 2")));
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    void shouldUpdateBuilding() throws Exception {
        Building building = createBuilding("Building 1");
        building.setId(1L);
        String json = objectMapper.writeValueAsString(new BuildingDto(building));

        given(buildingDao.getReferenceById(anyLong())).willReturn(building);

        mockMvc.perform(post("/api/buildings").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Building 1"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    void shouldDeleteBuilding() throws Exception {
        Building building = createBuilding("Building 1");

        given(buildingDao.findById(1L)).willReturn(Optional.of(building));

        mockMvc.perform(delete("/api/buildings/1").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    void shouldCreateBuilding() throws Exception {
        Building expectedBuilding = createBuilding("Building 1");
        String json = objectMapper.writeValueAsString(new BuildingDto(expectedBuilding));

        given(buildingDao.getReferenceById(anyLong())).willReturn(expectedBuilding);
        expectedBuilding.setId(1L);
        given(buildingDao.save(any())).willReturn(expectedBuilding);

        mockMvc.perform(post("/api/buildings").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Building 1"));
    }

    private Building createBuilding(String name) {
        Building building = new Building(name);
        return building;
    }
}