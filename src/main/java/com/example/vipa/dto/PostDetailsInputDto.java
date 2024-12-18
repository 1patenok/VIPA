package com.example.vipa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PostDetailsInputDto {

    private static final String FIELD_IS_MANDATORY_MESSAGE = "Это поле является обязательным для заполнения.";
    private static final String PRICE_CANNOT_BE_NEGATIVE_MESSAGE = "Цена не может быть отрицательной.";
    private static final String IMAGE_IS_MANDATORY_MESSAGE = "Необходимо прикрепить хотя бы одно изображение.";

    private int id;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    private String title;

    @NotNull(message = FIELD_IS_MANDATORY_MESSAGE)
    private int categoryId;

    private ClientPreviewDto author;

    //@NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Min(value = 0, message = PRICE_CANNOT_BE_NEGATIVE_MESSAGE)
    private int price;

    private String status;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Size(max = 500, message = "Описание не может быть длиннее 500 символов.")
    private String description;

    private String address;

    private LocalDate createdAt;

    @NotNull(message = FIELD_IS_MANDATORY_MESSAGE)
    @Size(min = 1, message = IMAGE_IS_MANDATORY_MESSAGE) // Проверяем, что хотя бы одно изображение прикреплено
    private List<MultipartFile> images;
}
