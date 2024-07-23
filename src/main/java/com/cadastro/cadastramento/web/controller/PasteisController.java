package com.cadastro.cadastramento.web.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.cadastramento.entity.Pasteis;
import com.cadastro.cadastramento.service.PasteisService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pasteis")
@Validated
public class PasteisController {

    private final PasteisService pasteisService;

    @PostMapping
    public ResponseEntity<Pasteis> criar(@Valid @RequestBody Pasteis p) {
        Pasteis pasteis = pasteisService.criar(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(pasteis);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Pasteis>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pasteisService.getById(id));
    }

    @GetMapping("/sabor/{sabor}")
    public ResponseEntity<Optional<Pasteis>> getBySabor(@PathVariable String sabor) {
        return ResponseEntity.ok(pasteisService.getBySabor(sabor));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        pasteisService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<Pasteis> updatePasteisPartial(@PathVariable Long id, @RequestBody Pasteis updatedPasteis) {
        Optional<Pasteis> pasteis = pasteisService.updatePasteisPartial(id, updatedPasteis);
        return pasteis.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
