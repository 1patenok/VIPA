package com.example.vipa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ClientPreviewDto {
    private int id;
    private String name;
    private String surname;
    private String profileImage;
}
