package com.cadastro.cadastramento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "O sabor não pode ser em branco")
    @Size(max = 50, message = "O sabor não pode ter mais de 50 caracteres")
    @Column(nullable = false, length = 50, unique = true)
    private String sabor;

    @NotNull(message = "O tamanho não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @NotBlank(message = "A descrição não pode ser em branco")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo")
    @Column(nullable = false)
    private Double preco;

    @Column(name = "imagem_url")
    private String imagemUrl;


    public enum Tamanho {
        PEQUENO, MEDIO, GRANDE
    }
}
