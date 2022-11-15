package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BuildingDaoCustomImpl implements BuildingDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    private BuildingDao buildingDao;

    @Override
    public Building findBuildingByName(String name) {
        String jpql = "select b from Building b where b.name = :name";
        return entityManager.createQuery(jpql, Building.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Building> findAllBuildings() {
        String jpql = "select b from Building b";
        return entityManager.createQuery(jpql, Building.class)
                .getResultList();
    }

    @Override
    public List<Room> findRoomsByBuilding(Long id) {
        String jpql = "select r from Room r where r.building.id = :id";
        return entityManager.createQuery(jpql, Room.class)
                .getResultList();
    }
}