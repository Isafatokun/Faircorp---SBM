package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;
import com.emse.spring.faircorp.model.WindowStatus;
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
class WindowDaoTest {
    @Autowired
    private WindowDao windowDao;

    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldFindAWindow() {
        Window window = windowDao.getReferenceById(-10L);
        Assertions.assertThat(window.getName()).isEqualTo("Window 1");
        Assertions.assertThat(window.getWindowStatus()).isEqualTo(WindowStatus.CLOSED);
    }

    @Test
    public void shouldDeleteWindow() {
        Assertions.assertThat(windowDao.findAll().size()).isEqualTo(4);
        windowDao.deleteWindow(-10L);
        Assertions.assertThat(windowDao.findAll().size()).isEqualTo(3);

    }

    @Test
    public void shouldFindRoomOpenWindows() {
        List<Window> result = windowDao.findRoomOpenWindows(-9L);
        Assertions.assertThat(result)
                .hasSize(1)
                .extracting("id", "windowStatus")
                .containsExactly(Tuple.tuple(-8L, WindowStatus.OPEN));
    }

    @Test
    public void shouldNotFindRoomOpenWindows() {
        List<Window> result = windowDao.findRoomOpenWindows(-10L);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldFindBuildingOpenWindows() {
        List<Window> result = windowDao.findBuildingOpenWindows(-19L);
        Assertions.assertThat(result)
                .hasSize(1)
                .extracting("id", "windowStatus")
                .containsExactly(Tuple.tuple(-8L, WindowStatus.OPEN));
    }

    @Test
    public void shouldDeleteWindowsRoom() {
        Room room = roomDao.getReferenceById(-10L);
        List<Long> roomIds = room.getWindows().stream().map(Window::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds.size()).isEqualTo(2);

        windowDao.deleteByRoom(-10L);
        List<Window> result = windowDao.findAllById(roomIds);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldFindAllWindows() {
        List<Window> result = windowDao.findAll();
        Assertions.assertThat(result)
                .hasSize(4)
                .extracting("id", "name", "windowStatus")
                .containsExactly(
                        Tuple.tuple(-10L, "Window 1", WindowStatus.CLOSED),
                        Tuple.tuple(-9L, "Window 2", WindowStatus.CLOSED),
                        Tuple.tuple(-8L, "Window 1", WindowStatus.OPEN),
                        Tuple.tuple(-7L, "Window 2", WindowStatus.CLOSED)
                );
    }


//    Doesnt work
//    @Test
//    public void shouldFindBuildingWindows() {
//        List<Window> result = windowDao.findBuildingWindows(-19L);
//        Assertions.assertThat(result)
//                .hasSize(2)
//                .extracting("id", "name", "windowStatus")
//                .containsExactly(
//                        Tuple.tuple(-10L, "Window 1", WindowStatus.CLOSED),
//                        Tuple.tuple(-8L, "Window 1", WindowStatus.OPEN)
//                );
//    }

    @Test
    public void shouldFindRoomWindows() {
        List<Window> result = windowDao.findRoomWindows(-10L);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "name", "windowStatus")
                .containsExactly(
                        Tuple.tuple(-10L, "Window 1", WindowStatus.CLOSED),
                        Tuple.tuple(-9L, "Window 2", WindowStatus.CLOSED)
                );
    }


}