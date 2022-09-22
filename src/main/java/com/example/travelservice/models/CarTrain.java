package com.example.travelservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "car_train")
public class CarTrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Car car;
    @ManyToOne(fetch = FetchType.EAGER)
    private Timetable timetable;
    private int sequence_number;

    public CarTrain(Car car_id, Timetable timetable_id, int sequence_number) {
        this.car = car_id;
        this.timetable = timetable_id;
        this.sequence_number = sequence_number;
    }
}
