package com.emse.spring.faircorp.dto;

import com.emse.spring.faircorp.model.Building;

import java.util.Set;
import java.util.stream.Collectors;

public class BuildingDto {

    private Long id;
    private String name;
    private Set<RoomDto> rooms;
    private Long outsideTemp;

    public BuildingDto() {

    }

    public BuildingDto(Building building) {
        this.id = building.getId();
        this.name = building.getName();
        this.outsideTemp = building.getOutsideTemp();
        if(building.getRooms() != null) {
            this.rooms = building.getRooms().stream().map(RoomDto::new).collect(Collectors.toSet());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(Set<RoomDto> rooms) {
        this.rooms = rooms;
    }
}
