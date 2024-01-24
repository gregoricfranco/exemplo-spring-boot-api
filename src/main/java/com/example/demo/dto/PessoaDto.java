package com.example.demo.dto;

import com.example.demo.models.Categoria;
import com.example.demo.models.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record PessoaDto(
        @NotBlank String nome,
        @NotNull Integer idade,
        @NotBlank String endereco,
        Categoria categoria
) {

    public Pessoa toPessoa() {
        return new Pessoa(nome, idade, endereco, categoria);
    }
}
