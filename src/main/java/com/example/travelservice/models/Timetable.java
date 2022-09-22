package com.example.travelservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private TrainRoute train_route;
    @ManyToOne(fetch = FetchType.EAGER)
    private Station departure_station;
    @ManyToOne(fetch = FetchType.EAGER)
    private Station arrival_station;
    private Date departureDate;
    private Date arrivalDate;
    private Time departureTime;
    private Time arrivalTime;

    public Timetable(TrainRoute train_route_id, Station departure_station_id, Station arrival_station_id,
                     Date departureDate, Date arrivalDate, Time departureTime, Time arrivalTime) {
        this.train_route = train_route_id;
        this.departure_station = departure_station_id;
        this.arrival_station = arrival_station_id;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}
