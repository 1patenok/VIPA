package com.example.vipa.dto;

import com.example.vipa.model.Post;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CategoryDto {
    private int id;
    private String name;
    private String image;
    private List<Post> posts;
}
