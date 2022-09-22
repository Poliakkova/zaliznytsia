package com.example.travelservice.models;

public enum Status {
    AVAILABLE("Вільний"), RESERVED("Бронь"), SOLD("Продано");

    private final String status;

    Status(String status) {
        this.status = status;
    }
}
