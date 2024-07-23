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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pasteis")
@Validated
@Tag(name = "Pasteis", description = "Endpoints para gerenciamento de pasteis")
public class PasteisController {

    private final PasteisService pasteisService;

    @Operation(summary = "Cria Pastel", description = "Cria um Pastel",
    tags = {"Pasteis"},
    responses = {
        @ApiResponse(description = "Criado", responseCode = "201",
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Pasteis.class)) 
            )
        }),
        @ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Pasteis> criar(@Valid @RequestBody Pasteis p) {
        Pasteis pasteis = pasteisService.criar(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(pasteis);
    }

    @Operation(summary = "Busca Pastel por Id", description = "Busca um Pastel pelo Id",
    tags = {"Pasteis"},
    responses = {
        @ApiResponse(description = "Ok", responseCode = "200",
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Pasteis.class)) 
            )
        }),
        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Pasteis>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pasteisService.getById(id));
    }

    @Operation(summary = "Busca Pastel por Sabor", description = "Busca um Pastel pelo Sabor",
    tags = {"Pasteis"},
    responses = {
        @ApiResponse(description = "Ok", responseCode = "200",
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Pasteis.class)) 
            )
        }),
        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @GetMapping("/sabor/{sabor}")
    public ResponseEntity<Optional<Pasteis>> getBySabor(@PathVariable String sabor) {
        return ResponseEntity.ok(pasteisService.getBySabor(sabor));
    }


    @Operation(summary = "Deleta Pastel", description = "Deleta um Pastel",
    tags = {"Pasteis"},
    responses = {
        @ApiResponse(description = "No Content", responseCode = "204",
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Pasteis.class)) 
            )
        }),
        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        pasteisService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza Pastel", description = "Atualiza um Pastel",
    tags = {"Pasteis"},
    responses = {
        @ApiResponse(description = "Ok", responseCode = "200",
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Pasteis.class)) 
            )
        }),
        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    @PatchMapping("/id/{id}")
    public ResponseEntity<Pasteis> updatePasteisPartial(@PathVariable Long id, @RequestBody Pasteis updatedPasteis) {
        Optional<Pasteis> pasteis = pasteisService.updatePasteisPartial(id, updatedPasteis);
        return pasteis.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
