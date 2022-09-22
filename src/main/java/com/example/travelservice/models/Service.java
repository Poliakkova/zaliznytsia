package com.example.travelservice.models;

public enum Service {
    BED("З білизною"), NO_BED("Без білизни");

    private final String service;

    Service(String service) {
        this.service = service;
    }
}
