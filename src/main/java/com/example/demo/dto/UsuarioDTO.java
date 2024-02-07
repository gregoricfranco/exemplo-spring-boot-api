package com.example.demo.dto;

import com.example.demo.enuns.Categoria;
import com.example.demo.model.Imovel;
import com.example.demo.model.Usuario;

import java.util.List;

public record UsuarioDTO(Long id, String nome, String email, String telefone,String validationToken, boolean isValidado, Categoria categoria, List<Imovel> imoveis) {
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone(),  usuario.getValidationToken(), usuario.isValidado(), usuario.getCategoria(), usuario.getImoveis());

    }

}
