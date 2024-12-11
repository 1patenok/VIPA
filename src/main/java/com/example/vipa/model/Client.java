package com.example.vipa.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "client")
@Accessors(chain = true)
public class Client {
    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    @ManyToMany
    @JoinTable(name = "favorite_post",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> favoritePosts;

    @ManyToMany
    @JoinTable(name = "cart_post",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> postsInCart;

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "dialog")
    private List<Dialog> dialogs;

    public void addFavorite(Post post) {
        favoritePosts.add(post);
    }

    public void removeFavorite(Post post) {
        favoritePosts.remove(post);
    }

    public void addToCart(Post post) {
        postsInCart.add(post);
    }

    public void removeFromCart(Post post) {
        postsInCart.remove(post);
    }
}
