package com.example.travelservice.models;

import lombok.Getter;

@Getter
public enum Type {
    SIT("Сидячий"), PLACE("Плацкарт"), KUPE("Купе");

    private final String type;

    Type(String type) {
        this.type = type;
    }
}
