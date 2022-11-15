package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Window;

import java.util.List;

public interface WindowDaoCustom {

    List<Window> findRoomOpenWindows(Long id);

    List<Window> findBuildingWindows(Long id);

    List<Window> findBuildingOpenWindows(Long id);

    List<Window> findRoomWindows(Long id);

    void deleteByBuilding(Long id);
}