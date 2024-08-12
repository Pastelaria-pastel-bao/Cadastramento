package com.cadastro.cadastramento.web.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/pasteis")
    public ResponseEntity<Page<Pasteis>> listarPasteis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pasteis> pasteisPage = pasteisService.listarPasteis(pageable);
        return ResponseEntity.ok(pasteisPage);
    }
}
