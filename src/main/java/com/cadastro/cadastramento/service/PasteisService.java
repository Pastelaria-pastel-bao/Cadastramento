package com.cadastro.cadastramento.service;

import com.cadastro.cadastramento.entity.Pasteis;
import com.cadastro.cadastramento.repository.PasteisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasteisService {

    private final PasteisRepository pasteisRepository;

    @Transactional
    public Pasteis criar(Pasteis pasteis) {
        return pasteisRepository.save(pasteis);
    }
}
