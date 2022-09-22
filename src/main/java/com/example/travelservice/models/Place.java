package com.example.travelservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int sit;
    @ManyToOne(fetch = FetchType.EAGER)
    private CarTrain car_train;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Place(int sit, CarTrain car_train_id, Status status) {
        this.sit = sit;
        this.car_train = car_train_id;
        this.status = status;
    }
}
