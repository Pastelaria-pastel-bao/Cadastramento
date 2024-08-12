package com.cadastro.cadastramento.web;

import static com.cadastro.cadastramento.commum.PasteisConstants.pastelCreate;
import static com.cadastro.cadastramento.commum.PasteisConstants.pastelCreated;
import static com.cadastro.cadastramento.commum.PasteisConstants.pastelEmpty;
import static com.cadastro.cadastramento.commum.PasteisConstants.pastelNull;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cadastro.cadastramento.entity.Pasteis;
import com.cadastro.cadastramento.entity.Pasteis.Tamanho;
import com.cadastro.cadastramento.exceptions.PastelNaoEncontradoException;
import com.cadastro.cadastramento.service.PasteisService;
import com.cadastro.cadastramento.web.controller.PasteisController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PasteisController.class)
public class PasteisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PasteisService service;

    @Test
    public void criarPastel_WithValidDate_ReturnCreated() throws JsonProcessingException, Exception{
        when(service.criar(pastelCreate)).thenReturn(pastelCreated);

        mockMvc.perform(post("/api/v1/pasteis")
        .content(objectMapper.writeValueAsString(pastelCreated))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    }

    @Test
    public void criarPastel_WithInvalidDate_ReturnBadRequest() throws JsonProcessingException, Exception{

        mockMvc.perform(post("/api/v1/pasteis")
        .content(objectMapper.writeValueAsString(pastelEmpty))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/v1/pasteis")
        .content(objectMapper.writeValueAsString(pastelNull))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void findById_WithExistingId_ReturnOk() throws Exception{
        when(service.getById(1L)).thenReturn(Optional.of(pastelCreated));

        mockMvc.perform(get("/api/v1/pasteis/id/1"))
        .andExpect(status().isOk());
    }

    @Test
    public void findById_WithUnexistingId_ReturnNotFound() throws Exception{
        when(service.getById(100L)).thenThrow(PastelNaoEncontradoException.class);

        mockMvc.perform(get("/api/v1/pasteis/id/100"))
        .andExpect(status().isNotFound());
    }

    @Test
    public void findBySabor_WithExistingSabor_ReturnOk() throws Exception{
        when(service.getBySabor("Calabresa")).thenReturn(Optional.of(pastelCreated));

        mockMvc.perform(get("/api/v1/pasteis/sabor/Calabresa"))
        .andExpect(status().isOk());
    }

    @Test
    public void findBySabor_WithUnexistingSabor_ReturnNotFound() throws Exception{
        when(service.getBySabor("Calabresa")).thenThrow(PastelNaoEncontradoException.class);

        mockMvc.perform(get("/api/v1/pasteis/sabor/Calabresa"))
        .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById_WithExistingId_ReturnNoContent(){
        assertThatCode(() -> mockMvc.perform(delete("/api/v1/pasteis/id/1"))).doesNotThrowAnyException();
    }

    @Test
    public void deleteById_WithNonExistenId_ReturnNotFound(){
        doThrow(new PastelNaoEncontradoException("Pastel não encontrado")).when(service).deleteById(100L);;
        assertThatThrownBy(() -> service.deleteById(100L)).isInstanceOf(PastelNaoEncontradoException.class);
    }

    @Test
    public void updatePastel_WithNonExistenId_ReturnNotFound(){
         Pasteis pastel = new Pasteis(1L, pastelCreated.getSabor(), Tamanho.MEDIO, "null",10.90, null);
         doThrow(new PastelNaoEncontradoException("Pastel não encontrado")).when(service).updatePasteisPartial(1L, pastel);

         assertThatThrownBy(() -> service.updatePasteisPartial(1L, pastel)).isInstanceOf(PastelNaoEncontradoException.class);

    }
}
