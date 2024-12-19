package com.example.vipa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_order")
@Accessors(chain = true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "price")
    private int price;

    @Column(name = "address")
    private String deliveryAddress;

    @Column(name = "delivery_method")
    @Enumerated(value = EnumType.STRING)
    private DeliveryMethod deliveryMethod;

    @Column(name = "status_of_delivery")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate orderDate;

    @Column(name = "delivery_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deliveryDate;

    @Column(name = "payment_method")
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @ManyToMany
    @JoinTable(name = "order_post",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> postsInOrder;
}
