package com.example.vipa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ClientDetailsDto {

    private static final String FIELD_IS_MANDATORY_MESSAGE = "Это поле является обязательным для заполнения.";
    private static final String EMAIL_IS_NOT_VALID_MESSAGE = "Вы ввели невалидный email.";
    private static final String PHONE_NUMBER_IS_NOT_VALID_MESSAGE = "Вы ввели невалидный номер телефона.";

    private int id;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    private String name;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    private String surname;

    @NotNull(message = FIELD_IS_MANDATORY_MESSAGE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$",
             message = PHONE_NUMBER_IS_NOT_VALID_MESSAGE)
    private String phoneNumber;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = EMAIL_IS_NOT_VALID_MESSAGE)
    private String email;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    private String password;

    @NotBlank(message = FIELD_IS_MANDATORY_MESSAGE)
    private String passwordConfirmation;
}
