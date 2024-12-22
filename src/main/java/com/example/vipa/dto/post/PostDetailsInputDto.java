package com.example.vipa.dto.post;

import com.example.vipa.dto.client.ClientPreviewDto;
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
    private static final String DESCRIPTION_IS_TOO_LONG_MESSAGE = "Длина описания не должна превышать 500 символов.";
    private static final String ADDRESS_IS_TOO_LONG_MESSAGE = "Длина адреса не должна превышать 100 символов.";

    @Size(min = 2, max = 50)
    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    private String title;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    private int categoryId;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Min(value = 0, message = PRICE_CANNOT_BE_NEGATIVE_MESSAGE)
    private int price;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Size(min = 10, max = 500, message = DESCRIPTION_IS_TOO_LONG_MESSAGE)
    private String description;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Size(min = 10, max = 100, message = ADDRESS_IS_TOO_LONG_MESSAGE)
    private String address;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Size(min = 1, message = IMAGE_IS_MANDATORY_MESSAGE)
    private List<MultipartFile> images;
}
