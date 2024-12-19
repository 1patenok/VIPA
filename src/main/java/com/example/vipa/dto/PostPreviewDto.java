package com.example.vipa.dto;

import com.example.vipa.model.Client;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PostPreviewDto {
    private int id;
    private String title;
    private int price;
    private String address;
    private ClientPreviewDto author;
    private String coverImagePath;
}
