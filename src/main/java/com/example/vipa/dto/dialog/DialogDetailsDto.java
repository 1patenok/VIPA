package com.example.vipa.dto.dialog;

import com.example.vipa.dto.client.ClientPreviewDto;
import com.example.vipa.dto.post.PostPreviewDto;
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
    private PostPreviewDto post;
    private ClientPreviewDto seller;
    private ClientPreviewDto customer;
    private List<MessageDto> messages;
}
