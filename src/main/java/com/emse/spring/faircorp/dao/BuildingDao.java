package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingDao extends JpaRepository<Building, Long> , BuildingDaoCustom {

    Building getReferenceById(Long id);

    Building getById(Long id);
}