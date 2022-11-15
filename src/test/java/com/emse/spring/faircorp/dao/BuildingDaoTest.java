package com.emse.spring.faircorp.dao;
import com.emse.spring.faircorp.model.Building;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BuildingDaoTest {
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldFindABuilding() {
        Building building = buildingDao.getReferenceById(-19L);
        Assertions.assertThat(building.getName()).isEqualTo("Building 1");
    }

    @Test
    public void shouldFindAllBuildings() {
        List<Building> buildings = buildingDao.findAllBuildings();
        Assertions.assertThat(buildings.size()).isEqualTo(2);
    }

    @Test
    public void shouldDeleteBuilding() {
        Assertions.assertThat(buildingDao.findAll().size()).isEqualTo(2);
        buildingDao.deleteById(-19L);
        Assertions.assertThat(roomDao.findAll().size()).isEqualTo(1);
    }

    @Test
    public void shouldFindBuildingByName() {
        Building building = buildingDao.findBuildingByName("Building 1");
        Assertions.assertThat(building.getId()).isEqualTo(-19L);
        Assertions.assertThat(building.getName()).isEqualTo("Building 1");
    }
}