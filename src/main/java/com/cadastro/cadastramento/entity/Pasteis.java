package com.cadastro.cadastramento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name= "pasteis")
public class Pasteis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String sabor;

    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    public enum Tamanho {
        PEQUENO, MEDIO, GRANDE
    }
}
