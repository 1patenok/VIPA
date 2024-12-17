package com.example.vipa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class MessageDto {
    private int id;
    private String text;
    private String senderName;
    private LocalDateTime timestamp;
}
