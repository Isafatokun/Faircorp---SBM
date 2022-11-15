package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.HeaterStatus;
import com.emse.spring.faircorp.model.WindowStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {
    private final WindowDao windowDao;
    private final RoomDao roomDao;
    private final HeaterDao heaterDao;
    private final BuildingDao buildingDao;

    public BuildingController(WindowDao windowDao, RoomDao roomDao, HeaterDao heaterDao, BuildingDao buildingDao) {
        this.windowDao = windowDao;
        this.roomDao = roomDao;
        this.heaterDao = heaterDao;
        this.buildingDao = buildingDao;
    }

    @PostMapping // (8)

    public BuildingDto create(@RequestBody BuildingDto dto) {
        Building building = null;

        if (dto.getId() == null) {
            building = buildingDao.save(new Building(dto.getName()));
        }
        else {
            building = buildingDao.getReferenceById(dto.getId());  // (9)
            building.setName(dto.getName());
        }

        return new BuildingDto(building);
    }

    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id) {
        return buildingDao.findById(id).map(BuildingDto::new).orElse(null);
    }

    @GetMapping(path = "/{id}/rooms")
    public List<RoomDto> findRoomsByBuilding(@PathVariable Long id) {
        return  roomDao.findRoomsByBuilding(id).stream().map(RoomDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}/windows")
    public List<WindowDto> findWindowsByBuilding(@PathVariable Long id) {
        return windowDao.findBuildingWindows(id).stream().map(WindowDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}/heaters")
    public List<HeaterDto> findHeatersByBuilding(@PathVariable Long id) {
        return heaterDao.findBuildingHeaters(id).stream().map(HeaterDto::new).collect(Collectors.toList());
    }

    @PutMapping(path = "/{id}/heaters/off")
    public List<HeaterDto> turnOffHeatersByBuilding(@PathVariable Long id) {
        List<HeaterDto> heaters = heaterDao.findBuildingHeaters(id).stream().map(HeaterDto::new).collect(Collectors.toList());
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

    @PutMapping(path = "/{id}/heaters/on")
    public List<HeaterDto> turnOnHeatersByBuilding(@PathVariable Long id) {
        List<HeaterDto> heaters = heaterDao.findBuildingHeaters(id).stream().map(HeaterDto::new).collect(Collectors.toList());
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

    @PutMapping(path = "/{id}/windows/close")
    public List<WindowDto> closeWindowsByBuilding(@PathVariable Long id) {
        List<WindowDto> windows = windowDao.findBuildingWindows(id).stream().map(WindowDto::new).collect(Collectors.toList());
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

    @PutMapping(path = "/{id}/windows/open")
    public List<WindowDto> openWindowsByBuilding(@PathVariable Long id) {
        List<WindowDto> windows = windowDao.findBuildingWindows(id).stream().map(WindowDto::new).collect(Collectors.toList());
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

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        Building building = buildingDao.getReferenceById(id);
        if (building!= null) {
            buildingDao.deleteById(id);
        }
    }
}