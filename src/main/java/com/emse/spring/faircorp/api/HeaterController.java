package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/heaters")
@Transactional

public class HeaterController {

    private final HeaterDao heaterDao;
    private final RoomDao roomDao;

    public HeaterController(HeaterDao heaterDao, RoomDao roomDao) {
        this.heaterDao = heaterDao;
        this.roomDao = roomDao;
    }

    @GetMapping
    public List<HeaterDto> findAll() {
        return heaterDao.findAll().stream().map(HeaterDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public HeaterDto findById(@PathVariable Long id) {
        return heaterDao.findById(id).map(HeaterDto::new).orElse(null);
    }


    @PutMapping(path = "/{id}/switch")
    public HeaterDto switchStatus(@PathVariable Long id) {
        Heater heater = heaterDao.findById(id).orElseThrow(IllegalArgumentException::new);
        heater.setHeaterStatus(heater.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF: HeaterStatus.ON);
        return new HeaterDto(heater);
    }

    @PostMapping // (8)
    public HeaterDto create(@RequestBody HeaterDto dto) {
        Room room;
        room = roomDao.getReferenceById(dto.getRoomId());
        Heater heater = null;

        if (dto.getId() == null) {
            heater = heaterDao.save(new Heater( dto.getName(), dto.getHeaterStatus(),dto.getPower(),room));
        }
        else {
            heater = heaterDao.getReferenceById(dto.getId());  // (9)
            heater.setHeaterStatus(dto.getHeaterStatus());
            heater.setPower(dto.getPower());
            heater.setRoom(room);
            heater.setName(dto.getName());
        }
        return new HeaterDto(heater);
    }

    @PutMapping(path = "/{id}/update")
    public HeaterDto update(@RequestBody HeaterDto dto) {
        Room room = roomDao.getReferenceById(dto.getRoomId());
        Heater heater = null;

        heater = heaterDao.getReferenceById(dto.getId());  // (9)
        heater.setHeaterStatus(dto.getHeaterStatus());
        heater.setPower(dto.getPower());
        heater.setRoom(room);
        heater.setName(dto.getName());

        return new HeaterDto(heater);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        heaterDao.deleteById(id);
    }
}