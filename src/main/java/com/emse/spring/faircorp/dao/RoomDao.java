package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDao extends JpaRepository<Room, Long>, RoomDaoCustom {

    Room getReferenceById(Long id);

    @Modifying
    @Query("delete from Room r where r.building.id =?1")
    void deleteByBuilding(Long id);

}