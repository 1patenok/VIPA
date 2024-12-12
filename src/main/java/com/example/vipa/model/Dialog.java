package com.example.vipa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Table(name = "dialog")
@Accessors(chain = true)
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dialog_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "client_id")
    private Client customer;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @OneToMany(mappedBy = "dialog")
    private List<Message> messages;

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }
}
