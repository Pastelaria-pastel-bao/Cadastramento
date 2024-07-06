package com.cadastro.cadastramento.service;

import com.cadastro.cadastramento.dto.PastelCriarDto;
import com.cadastro.cadastramento.entity.Pasteis;
import com.cadastro.cadastramento.repository.PasteisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasteisService {

    private final PasteisRepository pasteisRepository;

    @Transactional
    public Optional<Pasteis> getById(Long id) {
        return pasteisRepository.findById(id);
    }

    @Transactional
    public Pasteis criar(PastelCriarDto pasteisDto) {
        DozerBeanMapper dozer = new DozerBeanMapper();
        Pasteis p = dozer.map(pasteisDto, Pasteis.class);

        return pasteisRepository.save(p);
    }

    @Transactional
    public Optional<Pasteis> getBySabor(String sabor) {
        return pasteisRepository.findBySabor(sabor);
    }

    @Transactional
    public void deleteById(Long id) {
        pasteisRepository.deleteById(id);
    }

    @Transactional
    public Optional<Pasteis> updatePasteisPartial(Long id, PastelCriarDto updatedPasteis) {
        return pasteisRepository.findById(id).map(pasteis -> {
            if (updatedPasteis.getSabor() != null) {
                pasteis.setSabor(updatedPasteis.getSabor());
            }
            if (updatedPasteis.getTamanho() != null) {
                pasteis.setTamanho(updatedPasteis.getTamanho());
            }
            if (updatedPasteis.getDescricao() != null) {
                pasteis.setDescricao(updatedPasteis.getDescricao());
            }
            if (updatedPasteis.getPreco() != null) {
                pasteis.setPreco(updatedPasteis.getPreco());
            }
            return pasteisRepository.save(pasteis);
        });
    }
}
