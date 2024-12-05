package com.example.vipa.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NewClientDto {
    private String name;
    private String surname;
    private Date bigthDate;
    private String phoneNumber;
    private String email;
    private String password;
    private String passwordConfirmation;
}
