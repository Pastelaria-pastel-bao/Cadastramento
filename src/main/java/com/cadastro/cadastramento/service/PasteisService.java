package com.cadastro.cadastramento.service;

import com.cadastro.cadastramento.repository.PasteisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasteisService {

    private final PasteisRepository pasteisRepository;

    public PasteisService(PasteisRepository pasteisRepository) {
        this.pasteisRepository = pasteisRepository;
    }
}