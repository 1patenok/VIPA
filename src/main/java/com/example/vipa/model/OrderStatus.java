package com.example.vipa.model;

public enum OrderStatus {
    COMPLITED("Завершен"),
    PROCESSING("В обработке"),
    IN_DELIVERY("Доставляется"),
    READY_FOR_PICKUP("Готов к выдаче");

    public final String displayValue;

    OrderStatus(String displayValue) {
        this.displayValue = displayValue;
    }
}
