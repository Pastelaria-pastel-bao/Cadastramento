package com.cadastro.cadastramento.web.controller;

import com.cadastro.cadastramento.entity.Pasteis;
import com.cadastro.cadastramento.service.PasteisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pasteis")
public class PropostaController {

    private final PasteisService pasteisService;

    @PostMapping
    public ResponseEntity<Pasteis> criar(@RequestBody Pasteis p) {
        Pasteis pasteis = pasteisService.criar(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(pasteis);
    }
}
