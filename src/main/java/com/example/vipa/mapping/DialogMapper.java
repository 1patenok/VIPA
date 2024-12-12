package com.example.vipa.mapping;

import com.example.vipa.dto.DialogDetailsDto;
import com.example.vipa.dto.DialogPreviewDto;
import com.example.vipa.model.Dialog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DialogMapper {
    private final ModelMapper modelMapper;

    public DialogPreviewDto convertToDialogPreviewDto(Dialog dialog) {
        return modelMapper.map(dialog, DialogPreviewDto.class);
    }

    public DialogDetailsDto convertToDialogDetailsDto(Dialog dialog) {
        return modelMapper.map(dialog, DialogDetailsDto.class);
    }
}
