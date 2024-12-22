package com.example.vipa.dto.dialog;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
