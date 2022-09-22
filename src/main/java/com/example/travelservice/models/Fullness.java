package com.example.travelservice.models;

public enum Fullness {
    FULL("Повний"), STUDENT("Студентський"), KID("Дитячий"), BENEFIT("Пільговий");

    private final String fullness;

    Fullness(String fullness) {
        this.fullness = fullness;
    }
}
