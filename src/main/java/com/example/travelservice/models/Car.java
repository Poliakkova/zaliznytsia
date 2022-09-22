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
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String car;
    @Enumerated(EnumType.STRING)
    private Type type;
    private int sits_quantity;

    public Car(String car, Type type, int sits_quantity) {
        this.car = car;
        this.type = type;
        this.sits_quantity = sits_quantity;
    }
}
