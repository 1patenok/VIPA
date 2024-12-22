package com.example.vipa.dto.dialog;

import com.example.vipa.dto.client.ClientPreviewDto;
import com.example.vipa.dto.post.PostPreviewDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DialogPreviewDto {
    private int id;
    private ClientPreviewDto customer;
    private PostPreviewDto post;
}
