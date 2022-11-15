package com.emse.spring.faircorp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "BUILDING")
public class Building {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long outsideTemp;

    @Column(nullable = false)
    @OneToMany(mappedBy = "building" , cascade = CascadeType.REMOVE)
    private Set<Room> rooms;

    public Building() {
        //Internationally left Empty
    }

    public Building(String name) {
        this.name = name;
    }

    public Building(String name, Set<Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Long getOutsideTemp() {
        return outsideTemp;
    }

    public void setOutsideTemp(Long outsideTemp) {
        outsideTemp = outsideTemp;
    }
}
