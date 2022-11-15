package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;

import java.util.List;

public interface RoomDaoCustom {

//    void deleteByBuilding(Long id);

    Room findRoomByName(String name);

    List<Room> findRoomsByBuilding(Long id);

    void deleteByBuilding(Long id);
}