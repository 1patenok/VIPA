package com.example.vipa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "completed_order")
public class CompletedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "completed_order_id")
    private int completedOrderId;

    @Column(name = "price")
    private String name;

    @Column(name = "address")
    private String surname;

    @Column(name = "delivery_method")
    private Date birthDate;

//    @OneToOne(mappedBy = "completed_order", cascade = CascadeType.ALL)
//    private PaymentAccount paymentAccount;
}
