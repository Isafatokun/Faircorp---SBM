package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;
import com.emse.spring.faircorp.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
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

@WebMvcTest(HeaterController.class)
class HeaterControllerTest {

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
    void shouldCreateHeater() throws Exception {
        Heater expectedHeater = createHeater("heater 1");
        expectedHeater.setId(null);
        String json = objectMapper.writeValueAsString(new HeaterDto(expectedHeater));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedHeater.getRoom());
        given(heaterDao.save(any())).willReturn(expectedHeater);

        mockMvc.perform(post("/api/heaters").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("heater 1"));
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    void shouldLoadAHeaterAndReturnNullIfNotFound() throws Exception {
        given(heaterDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/heaters/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    void shouldGetAllHeaters() throws Exception {
        List<Heater> heaters = List.of(
                createHeater("Heater 1"),
                createHeater("Heater 2")
        );

        given(heaterDao.findAll()).willReturn(heaters);

        mockMvc.perform(get("/api/heaters").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("Heater 1","Heater 2")));
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    void shouldDeleteHeater() throws Exception {
        mockMvc.perform(delete("/api/heaters/999").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    void shouldSwitchHeater() throws Exception {
        Heater expectedHeater = createHeater("Heater 1");
        Assertions.assertThat(expectedHeater.getHeaterStatus()).isEqualTo(HeaterStatus.ON);

        given(heaterDao.findById(999L)).willReturn(Optional.of(expectedHeater));

        mockMvc.perform(put("/api/heaters/999/switch").accept(APPLICATION_JSON).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Heater 1"))
                .andExpect(jsonPath("$.heaterStatus").value("OFF"));
    }

    private Heater createHeater(String name) {
        Building building = buildingDao.getReferenceById(-19L);
        Room room = new Room("S1", 1, building);
        return new Heater(name, HeaterStatus.ON, 80L, room);
    }


}