package com.cadastro.cadastramento.web.controller;

import com.cadastro.cadastramento.repository.PasteisRepository;
import com.cadastro.cadastramento.service.PasteisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pasteis")
public class PropostaController {

    private final PasteisService pasteisService;
    private final PasteisRepository pasteisRepository;

    public PropostaController(PasteisService pasteisService, PasteisRepository pasteisRepository) {
        this.pasteisService = pasteisService;
        this.pasteisRepository = pasteisRepository;
    }
}