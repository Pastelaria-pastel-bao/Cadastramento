package com.cadastro.cadastramento.repository;

import com.cadastro.cadastramento.entity.Pasteis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasteisRepository extends JpaRepository<Pasteis, Long> {

}