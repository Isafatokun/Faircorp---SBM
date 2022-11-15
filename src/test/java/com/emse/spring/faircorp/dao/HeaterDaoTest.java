package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;
import com.emse.spring.faircorp.model.Room;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class HeaterDaoTest {
    @Autowired
    private HeaterDao heaterDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private BuildingDao buildingDao;

    @Test
    public void shouldFindAnHeater() {
        Heater heater = heaterDao.getReferenceById(-9L);
        Assertions.assertThat(heater.getName()).isEqualTo("Heater2");
        Assertions.assertThat(heater.getHeaterStatus()).isEqualTo(HeaterStatus.ON);
    }

    @Test
    public void shouldFindRoomHeaters() {
        List<Heater> result = heaterDao.findRoomHeaters(-10L);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "heaterStatus")
                .contains(Tuple.tuple(-9L, HeaterStatus.ON));
    }

    @Test
    public void shouldFindBuildingHeaters() {
        List<Heater> result = heaterDao.findBuildingHeaters(-19L);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "heaterStatus")
                .contains(Tuple.tuple(-9L, HeaterStatus.ON));
    }

    @Test
    public void shouldFindRoomOnHeaters() {
        List<Heater> result = heaterDao.findOnRoomHeaters(-10L);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "HeaterStatus")
                .contains(Tuple.tuple(-9L, HeaterStatus.ON));
    }

    @Test
    public void shouldFindRoomOffHeaters() {
        List<Heater> result = heaterDao.findOffRoomHeaters(-10L);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldFindBuildingOnHeaters() {
        List<Heater> result = heaterDao.findBuildingOnHeaters(-19L);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id","HeaterStatus")
                .contains(Tuple.tuple(-9L,HeaterStatus.ON));
    }

    @Test
    public void shouldFindBuildingOffHeaters() {
        List<Heater> result = heaterDao.findOffBuildingHeaters(-10L);
        Assertions.assertThat(result)
                .hasSize(0);
    }

    @Test
    public void shouldDeleteHeatersByRoom() {
        Room room = roomDao.getReferenceById(-10L);
        List<Long> roomIds = room.getHeaters().stream().map(Heater::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds.size()).isEqualTo(2);

        heaterDao.deleteByRoom(-10L);
        List<Heater> result = heaterDao.findAllById(roomIds);
        Assertions.assertThat(result).isEmpty();
    }

    //    Doesnt work (I will please like some feedback on why, thank you).
//    @Test
//    public void shouldDeleteHeatersByBuilding() {
//        Building building = buildingDao.getReferenceById(-19L);
//        List<Long> buildingIds = building.getRooms().stream().flatMap(r -> r.getHeaters().stream().map(Heater::getId)).collect(Collectors.toList());
//        Assertions.assertThat(buildingIds.size()).isEqualTo(2);
//
//        heaterDao.deleteByBuilding(-19L);
////        List<Heater> result = heaterDao.findBuildingHeaters(-19L);
////        Assertions.assertThat(result).isEmpty();
//    }

//    test to delete all windows in a building

}