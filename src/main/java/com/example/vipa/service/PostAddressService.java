package com.example.vipa.service;

import com.example.vipa.dto.PostAddressDto;
import com.example.vipa.mapping.PostAddressMapper;
import com.example.vipa.repository.PostAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostAddressService {
    private final PostAddressMapper postAddressMapper;
    private final PostAddressRepository postAddressRepository;
    @Transactional
    public List<PostAddressDto> getPostAddress() {
        return postAddressRepository.findAll().stream()
                .map(postAddressMapper::convertToPostAddressDto)
                .toList();

    }
}
