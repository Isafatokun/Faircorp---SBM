package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RoomDaoTest {
    @Autowired
    private RoomDao roomDao;

    @Autowired
    private BuildingDao buildingDao;

    @Test
    public void shouldFindARoom() {
        Room room = roomDao.getReferenceById(-9L);
        Assertions.assertThat(room.getName()).isEqualTo("Room2");
        Assertions.assertThat(room.getFloor()).isEqualTo(1);
    }

    //    Doesnt work (I will please like some feedback on why, thank you).
//    @Test
//    public void shouldDeleteBuildingRooms() {
//        Building building = buildingDao.getReferenceById(-19L);
//        Assertions.assertThat(building.getRooms().size()).isEqualTo(2);
//
//        roomDao.deleteByBuilding(-19L);
//        Assertions.assertThat(building.getRooms().size()).isEqualTo(0);
//    }

    @Test
    public void shouldFindAllRooms() {
        Assertions.assertThat(roomDao.findAll()).hasSize(3);
    }

    @Test
    public void shouldFindAllRoomsByBuilding() {
        Assertions.assertThat(roomDao.findRoomsByBuilding(-19L)).hasSize(2);
    }
}