package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Kaan on 20.3.2016.
 */

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private String reservationName;

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }

    Reservation() {
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", reservationName='" + reservationName + '\'' + '}';
    }

    public Long getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }
    
}
