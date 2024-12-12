package com.example.vipa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class DialogDetailsDto {
    private int id;
    private String postTitle;
    private ClientPreviewDto seller;
    private ClientPreviewDto customer;
    private List<MessageDto> messages;
}
