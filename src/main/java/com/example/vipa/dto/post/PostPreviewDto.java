package com.example.vipa.dto.post;

import com.example.vipa.dto.client.ClientPreviewDto;
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
    private ClientPreviewDto author;
    private String coverImagePath;
}
