package com.example.vipa.model;

public enum PostStatus {
    ACTIVE("Активный"),
    INACTIVE("Неактивный");

    public final String displayValue;

    PostStatus(String displayValue) {
        this.displayValue = displayValue;
    }
}
