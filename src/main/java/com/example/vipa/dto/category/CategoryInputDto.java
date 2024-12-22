package com.example.vipa.dto.category;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryInputDto {
    private int id;
    private String name;
    private MultipartFile imageFile;
}
