package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.HeaterStatus;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.WindowStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    private final WindowDao windowDao;
    private final RoomDao roomDao;
    private final HeaterDao heaterDao;
    private final BuildingDao buildingDao;

    public RoomController(WindowDao windowDao, RoomDao roomDao, HeaterDao heaterDao, BuildingDao buildingDao) {
        this.windowDao = windowDao;
        this.roomDao = roomDao;
        this.heaterDao = heaterDao;
        this.buildingDao = buildingDao; 
    }

    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomDto::new).orElse(null);
    }

    @PostMapping // (8)
    public RoomDto create(@RequestBody RoomDto dto) {
        Building building = buildingDao.getReferenceById(dto.getBuildingId());

        Room room = null;
        if (dto.getId() == null) {
            room = roomDao.save(new Room(dto.getName(), dto.getFloor(), dto.getCurrentTemperature(), dto.getTargetTemperature(), building));
        }
        else {
            room = roomDao.getReferenceById(dto.getId());
            room.setId(dto.getId());
            room.setFloor(dto.getFloor());
            room.setName(dto.getName());
            room.setCurrentTemperature(dto.getCurrentTemperature());
            room.setTargetTemperature(dto.getTargetTemperature());
        }
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        Room room = roomDao.getReferenceById(id);
        if (room != null) {
            roomDao.deleteById(id);
        }
    }

//get all heaters of a room
    @GetMapping(path = "/{id}/heaters")
    public List<HeaterDto> findRoomHeaters(@PathVariable Long id) {
        return heaterDao.findRoomHeaters(id).stream().map(HeaterDto::new).collect(Collectors.toList());
    }

    @PutMapping(path = "/{id}/heaters/on")
    public List<HeaterDto> turnOnHeatersByRoom(@PathVariable Long id) {
        List<HeaterDto> heaters = heaterDao.findRoomHeaters(id).stream().map(HeaterDto::new).collect(Collectors.toList());
        for (HeaterDto heater : heaters) {
            if (heater.getHeaterStatus() == HeaterStatus.OFF) {
                heater.setHeaterStatus(HeaterStatus.ON);
            }
            else {
                heater.setHeaterStatus(HeaterStatus.ON);
            }
        }
        return heaters;
    }

    @PutMapping(path = "/{id}/heaters/off")
    public List<HeaterDto> turnOffHeatersByRoom(@PathVariable Long id) {
        List<HeaterDto> heaters = heaterDao.findRoomHeaters(id).stream().map(HeaterDto::new).collect(Collectors.toList());
        for (HeaterDto heater : heaters) {
            if (heater.getHeaterStatus() == HeaterStatus.ON) {
                heater.setHeaterStatus(HeaterStatus.OFF);
            }
            else {
                heater.setHeaterStatus(HeaterStatus.OFF);
            }
        }
        return heaters;
    }

    @DeleteMapping(path = "/{id}/heaters")
    public void deleteRoomHeaters(@PathVariable Long id) {
        heaterDao.deleteByRoom(id);
    }

    @PutMapping(path = "/{id}/windows/")
    public List<WindowDto> findRoomWindows(@PathVariable Long id) {
        return windowDao.findRoomWindows(id).stream().map(WindowDto::new).collect(Collectors.toList());
    }

    @PutMapping(path = "/{id}/windows/open")
    public List<WindowDto> openRoomWindows(@PathVariable Long id) {
        List<WindowDto> windows = windowDao.findRoomWindows(id).stream().map(WindowDto::new).collect(Collectors.toList());
        for (WindowDto window : windows) {
            if (window.getWindowStatus() == WindowStatus.CLOSED) {
                window.setWindowStatus(WindowStatus.OPEN);
            }
            else {
                window.setWindowStatus(WindowStatus.OPEN);
            }
        }
        return windows;
    }

    @PutMapping(path = "/{id}/windows/close")
    public List<WindowDto> closeRoomWindows(@PathVariable Long id) {
        List<WindowDto> windows = windowDao.findRoomWindows(id).stream().map(WindowDto::new).collect(Collectors.toList());
        for (WindowDto window : windows) {
            if (window.getWindowStatus() == WindowStatus.OPEN) {
                window.setWindowStatus(WindowStatus.CLOSED);
            }
            else {
                window.setWindowStatus(WindowStatus.CLOSED);
            }
        }
        return windows;
    }

    @DeleteMapping(path = "/{id}/windows")
    public void deleteRoomWindows(@PathVariable Long id) {
        windowDao.deleteByRoom(id);
    }
}
