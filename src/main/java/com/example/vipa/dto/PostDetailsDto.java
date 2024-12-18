package com.example.vipa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PostDetailsDto {

    private static final String FIELD_IS_MANDATORY_MESSAGE = "Это поле является обязательным для заполнения.";
    private static final String PRICE_CANNOT_BE_NEGATIVE_MESSAGE = "Цена не может быть отрицательной.";

    private int id;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    private String title;

    private int categoryId;

    private ClientPreviewDto author;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Min(value = 0, message = PRICE_CANNOT_BE_NEGATIVE_MESSAGE)
    private int price;
    private String status;
    private String description;
    private String address;
    private LocalDate createdAt;
    private List<MultipartFile> images;
}
