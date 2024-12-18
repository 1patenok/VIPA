package com.example.vipa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "post")
@Accessors(chain = true)
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "number_of_views")
    private int numberOfViews;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> images;

    @OneToMany(mappedBy = "post")
    private List<Dialog> dialogs;

    @ManyToMany(mappedBy = "favoritePosts")
    private List<Client> clientsWithPostInFavorites; // данное поле представляет клиентов, которые добавили данное объявление в избранное

    @ManyToMany(mappedBy = "postsInCart")
    private List<Client> clientsWithPostInCart; // данное поле представляет клиентов, которые добавили данное объявление в корзину

    @ManyToMany(mappedBy = "postsInOrder")
    private List<Order> ordersWithPost;


    @Override
    public String toString() {
        return "Post{" +
                "category=" + category +
                ", client=" + author +
                ", images=" + images +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", postId=" + id +
                '}';
    }


}
