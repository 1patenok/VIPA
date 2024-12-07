package com.example.vipa.dto;

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
    private int clientId;
    private String name;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String phoneNumber;
    private String email;
    private String password;
    private String passwordConfirmation;
}
