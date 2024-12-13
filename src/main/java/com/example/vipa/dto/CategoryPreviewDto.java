package com.example.vipa.dto;

import com.example.vipa.model.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryPreviewDto {
    private int id;
    private String name;
}
