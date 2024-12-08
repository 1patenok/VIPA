package com.example.vipa.dto;

import com.example.vipa.model.PostImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PostDetailsDto {
    private int postId;
    private String title;
    private int price;
    private String status;
    private String description;
    private String address;
    private List<PostImageDto> images;
}
