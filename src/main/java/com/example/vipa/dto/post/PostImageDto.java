package com.example.vipa.dto.post;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PostImageDto {
    private int id;

    private String url;
}
