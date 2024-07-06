package com.cadastro.cadastramento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter 
public class PastelCriarDto {

    private String sabor;

    private Tamanho tamanho;

    private String descricao;

    private String preco;

    public enum Tamanho {
        PEQUENO, MEDIO, GRANDE
    }
}
