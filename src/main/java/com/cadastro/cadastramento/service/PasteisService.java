package com.cadastro.cadastramento.service;

import com.cadastro.cadastramento.entity.Pasteis;
import com.cadastro.cadastramento.exceptions.DatabaseException;
import com.cadastro.cadastramento.exceptions.InvalidInputException;
import com.cadastro.cadastramento.exceptions.PastelDuplicadoException;
import com.cadastro.cadastramento.exceptions.PastelNaoEncontradoException;
import com.cadastro.cadastramento.repository.PasteisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        try {
            if (id <= 0) {
                throw new InvalidInputException("ID inválido");
            }
            return Optional.ofNullable(pasteisRepository.findById(id)
                    .orElseThrow(() -> new PastelNaoEncontradoException("Pastel não encontrado")));
        } catch (PastelNaoEncontradoException | InvalidInputException ex) {
            log.error("Erro ao buscar pastel por ID: {}", id, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao buscar pastel por ID: {}", id, ex);
            throw new DatabaseException("Erro no banco de dados");
        }
    }

    @Transactional
    public Pasteis criar(Pasteis pasteis) {
        try {
            if (pasteisRepository.findBySabor(pasteis.getSabor()).isPresent()) {
                throw new PastelDuplicadoException("Pastel com este sabor já existe");
            }
            return pasteisRepository.save(pasteis);
        } catch (PastelDuplicadoException | InvalidInputException ex) {
            log.error("Erro ao criar pastel: {}", pasteis, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar pastel: {}", pasteis, ex);
            throw new DatabaseException("Erro no banco de dados");
        }
    }

    @Transactional
    public Optional<Pasteis> getBySabor(String sabor) {
        try {
            if (sabor == null || sabor.isEmpty()) {
                throw new InvalidInputException("Sabor inválido");
            }
            return Optional.ofNullable(pasteisRepository.findBySabor(sabor)
                    .orElseThrow(() -> new PastelNaoEncontradoException("Pastel não encontrado")));
        } catch (PastelNaoEncontradoException | InvalidInputException ex) {
            log.error("Erro ao buscar pastel por sabor: {}", sabor, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao buscar pastel por sabor: {}", sabor, ex);
            throw new DatabaseException("Erro no banco de dados");
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            if (id <= 0) {
                throw new InvalidInputException("ID inválido");
            }
            if (!pasteisRepository.existsById(id)) {
                throw new PastelNaoEncontradoException("Pastel não encontrado");
            }
            pasteisRepository.deleteById(id);
        } catch (PastelNaoEncontradoException | InvalidInputException ex) {
            log.error("Erro ao deletar pastel por ID: {}", id, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao deletar pastel por ID: {}", id, ex);
            throw new DatabaseException("Erro no banco de dados");
        }
    }




    @Transactional
    public Optional<Pasteis> updatePasteisPartial(Long id, Pasteis updatedPasteis) {
        try {
            if (id <= 0) {
                throw new InvalidInputException("ID inválido");
            }

            Optional<Pasteis> existingPasteis = pasteisRepository.findById(id);
            if (!existingPasteis.isPresent()) {
                throw new PastelNaoEncontradoException("Pastel não encontrado");
            }

            if (!existingPasteis.get().getSabor().equals(updatedPasteis.getSabor())
                    && pasteisRepository.findBySabor(updatedPasteis.getSabor()).isPresent()) {
                throw new PastelDuplicadoException("Pastel com este sabor já existe");
            }
            return existingPasteis.map(pasteis -> {
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
        } catch (InvalidInputException ex) {
            log.error("Erro ao atualizar parcialmente pastel por ID: {}", id, ex);
            throw ex;
        } catch (PastelNaoEncontradoException ex) {
            log.error("Pastel não encontrado para o ID: {}", id, ex);
            throw ex;
        } catch (PastelDuplicadoException ex) {
            log.error("Erro ao atualizar pastel: {}", updatedPasteis, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar pastel parcialmente por ID: {}", id, ex);
            throw new DatabaseException("Erro no banco de dados"); // Lança uma nova exceção de banco de dados
        }
    }
}









