package com.example.vipa.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@Setter
@Entity
@Table(name = "message")
@Accessors(chain = true)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int id;

    @Column(name = "text")
    private String text;


    @Column(name = "send_timestamp")
    private LocalDateTime sendDateTime;

    @Column(name = "sender_name")
    private String senderName;

    @ManyToOne
    @JoinColumn(name = "dialog_id", referencedColumnName = "dialog_id")
    private Dialog dialog;


/*    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client recipient;*/
}
