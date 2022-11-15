package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Room;

import java.util.List;

public interface BuildingDaoCustom {

    Building findBuildingByName(String name);

    List<Building> findAllBuildings();

    List<Room> findRoomsByBuilding(Long id);
}