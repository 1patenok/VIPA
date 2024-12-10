package com.example.vipa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "completed_order")
@Accessors(chain = true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "completed_order_id")
    private int id;

    @Column(name = "price")
    private int price;

    @Column(name = "address")
    private String deliveryAddress;

    @Column(name = "delivery_method")
    @Enumerated(value = EnumType.STRING)
    private DeliveryMethod deliveryMethod;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @ManyToMany
    @JoinTable(name = "order_post",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> postsInOrder;

//    @OneToOne(mappedBy = "completed_order", cascade = CascadeType.ALL)
//    private PaymentAccount paymentAccount;
}
