package com.example.vipa.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ClientDetailsDto {

    private static final String FIELD_IS_MANDATORY_MESSAGE = "Это поле является обязательным для заполнения.";
    private static final String EMAIL_IS_NOT_VALID_MESSAGE = "Вы ввели невалидный email.";
    private static final String PHONE_NUMBER_IS_NOT_VALID_MESSAGE = "Вы ввели невалидный номер телефона.";
    private static final String INVALID_NAME_SIZE_MESSAGE = "Длина имени должна быть от 2 до 20 букв.";
    private static final String INVALID_SURNAME_SIZE_MESSAGE = "Длина фамилии должна быть от 2 до 20 букв.";
    private static final String INVALID_PASSWORD_SIZE_MESSAGE = "Длина пароля должна быть не менее 8 символов.";

    private int id;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Size(min = 2, max = 20, message = INVALID_NAME_SIZE_MESSAGE)
    private String name;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Size(min = 2, max = 20, message = INVALID_SURNAME_SIZE_MESSAGE)
    private String surname;

//    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$",
            message = PHONE_NUMBER_IS_NOT_VALID_MESSAGE)
    private String phoneNumber;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = EMAIL_IS_NOT_VALID_MESSAGE)
    private String email;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Size(min = 8, message = INVALID_PASSWORD_SIZE_MESSAGE)
    private String password;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Size(min = 8, message = INVALID_PASSWORD_SIZE_MESSAGE)
    private String passwordConfirmation;
}
