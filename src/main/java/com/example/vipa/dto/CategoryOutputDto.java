package com.example.vipa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryOutputDto {
    private int id;
    private String name;
    private String imagePath;
}
