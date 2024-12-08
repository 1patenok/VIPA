package com.example.vipa.dto;

import com.example.vipa.model.PostImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PostPreviewDto {
    private int postId;
    private String title;
    private int price;
    private String address;
    private PostImageDto coverImage;
}
