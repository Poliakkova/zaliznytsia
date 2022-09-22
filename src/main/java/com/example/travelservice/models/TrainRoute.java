package com.example.travelservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "train_route")
public class TrainRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String train;
    private String route;
    private float travel_time;

    public TrainRoute(String train, String route, float travel_time) {
        this.train = train;
        this.route = route;
        this.travel_time = travel_time;
    }
}
