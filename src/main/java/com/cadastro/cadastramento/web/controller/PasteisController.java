package com.cadastro.cadastramento.web.controller;

import java.util.List;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/pasteis")
    public ResponseEntity<Page<Pasteis>> listarPasteis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pasteis> pasteisPage = pasteisService.listarPasteis(pageable);
        return ResponseEntity.ok(pasteisPage);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = pasteisService.saveImage(id, file);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer upload da imagem");
        }
    }

    @DeleteMapping("/delete-image/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        try {
            pasteisService.deleteImage(id);
            return ResponseEntity.ok("Imagem deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar a imagem");
        }
    }



    @GetMapping("/search")
    public ResponseEntity<List<Pasteis>> searchProducts(@RequestParam("q") String query) {
        List<Pasteis> produtos = pasteisService.buscarPasteis(query);
        return ResponseEntity.ok(produtos);
    }



}
