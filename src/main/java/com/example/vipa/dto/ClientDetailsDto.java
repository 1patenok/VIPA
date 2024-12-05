package com.example.vipa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ClientDetailsDto {
    private String name;
    private String surname;
    private Date birthDate;
    private String phoneNumber;
    private String email;
    private String password;
    private String passwordConfirmation;
}
