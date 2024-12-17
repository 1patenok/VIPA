package com.example.vipa.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DialogPreviewDto {
    private int id;
    private String receiverName;
    private String postTitle;
}
