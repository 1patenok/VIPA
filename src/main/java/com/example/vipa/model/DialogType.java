package com.example.vipa.model;

public enum DialogType {
    AS_SELLER("Продажа"),
    AS_CUSTOMER("Покупка");

    public final String displayValue;

    DialogType(String displayValue) {
        this.displayValue = displayValue;
    }
}
