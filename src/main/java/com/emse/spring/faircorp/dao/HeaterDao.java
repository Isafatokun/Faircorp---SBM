package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeaterDao extends JpaRepository<Heater, Long> , HeaterDaoCustom {

    Heater getReferenceById(Long id);

    @Modifying
    @Query("delete from Heater h where h.room.id =?1")
    void deleteByRoom(Long id);

    @Modifying
    @Query("delete from Heater h where h.room.building.id =?1")
    void deleteByBuilding(Long id);
}