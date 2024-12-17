package com.example.vipa.model;

public enum DeliveryMethod {

    COURIER("Курьером"),
    DELIVERY_POINT("В пункт выдачи");

    public final String displayValue;

    DeliveryMethod(String displayValue) {
        this.displayValue = displayValue;
    }
}
