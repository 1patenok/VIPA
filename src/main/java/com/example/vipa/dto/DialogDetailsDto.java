package com.example.vipa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DialogDetailsDto {
    private int id;
    private String postName;
    private ClientPreviewDto seller;
    private ClientPreviewDto customer;
    private List<MessageDto> messages;
}
