package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RoomDaoCustomImpl implements RoomDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteByBuilding(Long id) {
        String jpql = "delete from Room r where r.building.id = :id";
        entityManager.createQuery(jpql, Room.class)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Room findRoomByName(String name) {
        String jpql = "select r from Room r where r.name = :name";
        return entityManager.createQuery(jpql, Room.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Room> findRoomsByBuilding(Long id) {
        String jpql = "select r from Room r where r.building.id = :id";
        return entityManager.createQuery(jpql, Room.class)
                .setParameter("id", id)
                .getResultList();
    }
}