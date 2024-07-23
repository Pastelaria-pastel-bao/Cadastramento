package com.cadastro.cadastramento.service;

import static com.cadastro.cadastramento.commum.PasteisConstants.pastelCreate;
import static com.cadastro.cadastramento.commum.PasteisConstants.pastelCreated;
import static com.cadastro.cadastramento.commum.PasteisConstants.pastelEmpty;
import static com.cadastro.cadastramento.commum.PasteisConstants.pastelNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cadastro.cadastramento.entity.Pasteis;
import com.cadastro.cadastramento.exceptions.InvalidInputException;
import com.cadastro.cadastramento.exceptions.PastelNaoEncontradoException;
import com.cadastro.cadastramento.repository.PasteisRepository;

@ExtendWith(MockitoExtension.class)
public class PasteisServiceTest {

    @InjectMocks
    private PasteisService service;

    @Mock
    private PasteisRepository repository;

    @Test
    public void createPastel_WithValidData_ReturnPastel(){
        when(service.criar(pastelCreate)).thenReturn(pastelCreated);

        Pasteis pastel = service.criar(pastelCreate);

        assertThat(pastel).isEqualTo(pastelCreated);
    }
    
    @Test
    public void createPastel_WithInvalidData_ThrowException(){
        when(service.criar(pastelNull)).thenThrow(new InvalidInputException("Dados Invalidos"));

        assertThatThrownBy(() -> service.criar(pastelNull)).isInstanceOf(InvalidInputException.class);

        when((service.criar(pastelEmpty))).thenThrow(new InvalidInputException("Dados Invalido"));

        assertThatThrownBy(() -> service.criar(pastelEmpty)).isInstanceOf(InvalidInputException.class);
    }

    @Test
    public void getById_WithExistingId_ReturnPastel(){
        when(repository.findById(1L)).thenReturn(Optional.of(pastelCreated));
        //when(service.getById(1L)).thenReturn(Optional.of(pastelCreated));

        Optional<Pasteis> pastel = service.getById(1L);

        assertThat(pastel.get()).isEqualTo(pastelCreated);
    }

    @Test
    public void getById_WithNonExistenId_ReturnExpection(){
        when(repository.findById(1L)).thenThrow(PastelNaoEncontradoException.class);

        assertThatThrownBy(() -> service.getById(1L)).isInstanceOf(PastelNaoEncontradoException.class);
    }

    @Test
    public void getBySabor_WithExistingSabor_ReturnPastel(){
        when(repository.findBySabor("Calabresa")).thenReturn(Optional.of(pastelCreated));

        Optional<Pasteis> pastel = service.getBySabor("Calabresa");

        assertThat(pastel.get()).isEqualTo(pastelCreated);
    }

    @Test
    public void getBySabor_WithNonExistenSabor_ReturnException(){
        when(repository.findBySabor("Calabresa")).thenThrow(PastelNaoEncontradoException.class);

        assertThatThrownBy(() -> service.getBySabor("Calabresa")).isInstanceOf(PastelNaoEncontradoException.class);
    }

    @Test
    public void deleteById_WithExistingId_ReturnVoid(){
        when(repository.existsById(1L)).thenReturn(true);
        assertThatCode(() -> service.deleteById(1L)).doesNotThrowAnyException();
    }

    @Test
    public void deleteById_WithNonExistenId_ReturnVoid(){
        assertThatThrownBy(() -> service.deleteById(1L)).isInstanceOf(PastelNaoEncontradoException.class);
    }
    
   
}
