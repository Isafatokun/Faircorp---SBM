package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WindowDao extends JpaRepository<Window, Long>, WindowDaoCustom {

    Window getReferenceById(Long id);

    @Modifying
    @Query("delete from Window w where w.id =?1")
    void deleteWindow(Long id);

    @Modifying
    @Query("delete from Window w where w.room.id =?1")
    void deleteByRoom(Long id);
}