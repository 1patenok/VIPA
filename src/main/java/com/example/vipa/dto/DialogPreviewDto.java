package com.example.vipa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DialogPreviewDto {
    private int id;
    private ClientPreviewDto customer;
    private PostPreviewDto post;
}
