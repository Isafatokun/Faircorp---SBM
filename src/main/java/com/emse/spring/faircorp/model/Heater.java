package com.emse.spring.faircorp.model;

import javax.persistence.*;

@Entity
public class Heater {
    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String name;

//    @Column(nullable = false)
    private Long power;

    @ManyToOne
    private Room room;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HeaterStatus heaterStatus;

    public Heater() {
        //Internationally left Empty
    }
    public Heater(String name, HeaterStatus heaterStatus, Room room) {
        this.name = name;
        this.heaterStatus = heaterStatus;
        this.room = room;
    }

    public Heater(String name, HeaterStatus heaterStatus) {
        this.name = name;
        this.heaterStatus = heaterStatus;
    }

    public Heater(String name, HeaterStatus heaterStatus, Long power, Room room) {
        this.name = name;
        this.heaterStatus = heaterStatus;
        this.power = power;
        this.room = room;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public HeaterStatus getHeaterStatus() {
        return heaterStatus;
    }

    public void setHeaterStatus(HeaterStatus heaterStatus) {
        this.heaterStatus = heaterStatus;
    }
}
