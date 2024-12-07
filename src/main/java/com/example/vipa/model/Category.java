package com.example.vipa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column(name = "name_category")
    private String nameCategory;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Post> post;
}
