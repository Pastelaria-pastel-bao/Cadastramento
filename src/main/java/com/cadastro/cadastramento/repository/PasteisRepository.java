package com.cadastro.cadastramento.repository;

import com.cadastro.cadastramento.entity.Pasteis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PasteisRepository extends JpaRepository<Pasteis, Long> {
    Optional<Pasteis> findBySabor(String sabor);


    @Query("SELECT p FROM Pasteis p WHERE LOWER(p.sabor) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            " LOWER(p.descricao) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    List<Pasteis> buscarPasteis(@Param("keyword") String keyword);
}

