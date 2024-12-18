package com.example.vipa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PostPreviewDto {
    private int id;
    private String title;
    private int price;
    private String address;
    private String authorName;
    private String coverImagePath;
}
