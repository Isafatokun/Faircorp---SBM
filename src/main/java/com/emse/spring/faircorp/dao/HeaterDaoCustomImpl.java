package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class HeaterDaoCustomImpl implements HeaterDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Heater> findRoomHeaters(Long id) {
        String jpql = "select h from Heater h where h.room.id = :id";
        return entityManager.createQuery(jpql, Heater.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Heater> findBuildingHeaters(Long id) {
        String jpql = "select h from Heater h where h.room.building.id = :id";
        return entityManager.createQuery(jpql, Heater.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Heater> findOnRoomHeaters(Long id) {
        String jpql = "select h from Heater h where h.room.id = :id and h.heaterStatus= :status";
        return entityManager.createQuery(jpql, Heater.class)
                .setParameter("id", id)
                .setParameter("status", HeaterStatus.ON)
                .getResultList();
    }

    @Override
    public List<Heater> findOffRoomHeaters(Long id) {
        String jpql = "select h from Heater h where h.room.id = :id and h.heaterStatus= :status";
        return entityManager.createQuery(jpql, Heater.class)
                .setParameter("id", id)
                .setParameter("status", HeaterStatus.OFF)
                .getResultList();
    }

    @Override
    public List<Heater> findBuildingOnHeaters(Long id) {
        String jpql = "select h from Heater h where h.room.building.id = :id and h.heaterStatus= :status";
        return entityManager.createQuery(jpql, Heater.class)
                .setParameter("id", id)
                .setParameter("status", HeaterStatus.ON)
                .getResultList();
    }

    @Override
    public List<Heater> findOffBuildingHeaters(Long id) {
        String jpql = "select h from Heater h where h.room.building.id = :id and h.heaterStatus= :status";
        return entityManager.createQuery(jpql, Heater.class)
                .setParameter("id", id)
                .setParameter("status", HeaterStatus.OFF)
                .getResultList();
    }

//    @Override
//    public void deleteByBuilding(Long id) {
//        String jpql = "delete from Heater h where h.room.building.id = :id";
//        entityManager.createQuery(jpql)
//                .setParameter("id", id)
//                .executeUpdate();
//    }
}