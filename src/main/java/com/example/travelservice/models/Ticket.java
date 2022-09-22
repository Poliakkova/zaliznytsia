package com.example.travelservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date purchase_date;
    private Time purchase_time;
    private String first_name;
    private String second_name;
    @ManyToOne(fetch = FetchType.EAGER)
    private Place place;
    @ManyToOne(fetch = FetchType.EAGER)
    private Timetable timetable;
    private float cost;
    @Enumerated(EnumType.STRING)
    private Fullness fullness;
    @Enumerated(EnumType.STRING)
    private Service service;

    public Ticket(Date purchase_date, Time purchase_time, String first_name, String second_name,
                  Place place_id, Timetable timetable_id, float cost, Fullness fullness, Service service) {
        this.purchase_date = purchase_date;
        this.purchase_time = purchase_time;
        this.first_name = first_name;
        this.second_name = second_name;
        this.place = place_id;
        this.timetable = timetable_id;
        this.cost = cost;
        this.fullness = fullness;
        this.service = service;
    }
}
