package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;

import java.util.List;

public interface HeaterDaoCustom {

    //    Getting all Heaters
    List<Heater> findRoomHeaters(Long id);

    List<Heater> findBuildingHeaters(Long id);

    //    Listing Heaters with Status On
    List<Heater> findOnRoomHeaters(Long id);

    List<Heater> findOffRoomHeaters(Long id);

    //    Heaters with Status Off
    List<Heater> findBuildingOnHeaters(Long id);

    List<Heater> findOffBuildingHeaters(Long id);

//    void deleteByBuilding(Long id);
}
