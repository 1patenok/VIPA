package com.example.vipa.model;

public enum PaymentMethod {
    BY_CARD("Картой"),
    CASH("Наличными при получении"),
    BY_CARD_AFTER_DELIVERY("Картой при получении");

    public final String displayValue;

    PaymentMethod(String displayValue) {
        this.displayValue = displayValue;
    }
}
