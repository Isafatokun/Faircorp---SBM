package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Window;
import com.emse.spring.faircorp.model.WindowStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class WindowDaoCustomImpl implements WindowDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Window> findRoomOpenWindows(Long id) {
        String jpql = "select w from Window w where w.room.id = :id and w.windowStatus= :status";
        return entityManager.createQuery(jpql, Window.class)
                .setParameter("id", id)
                .setParameter("status", WindowStatus.OPEN)
                .getResultList();
    }

    @Override
    public List<Window> findBuildingWindows(Long id) {
        String jpql = "select w from Window w where w.room.building.id = :id";
        return entityManager.createQuery(jpql, Window.class)
                .setParameter("id", id)
                .getResultList();
    }


    @Override
    public List<Window> findRoomWindows(Long id) {
        String jpql = "select w from Window w where w.room.id = :id";
        return entityManager.createQuery(jpql, Window.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Window> findBuildingOpenWindows(Long id) {
        String jpql = "select w from Window w where w.room.building.id = :id and w.windowStatus= :status";
        return entityManager.createQuery(jpql, Window.class)
                .setParameter("id", id)
                .setParameter("status", WindowStatus.OPEN)
                .getResultList();
    }

    @Override
    public void deleteByBuilding(Long id) {
        String jpql = "delete from Window w where w.room.building.id = :id";
        entityManager.createQuery(jpql, Window.class)
                .setParameter("id", id)
                .executeUpdate();
    }
}