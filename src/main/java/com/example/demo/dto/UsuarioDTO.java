package com.example.demo.dto;

import com.example.demo.enuns.Categoria;
import com.example.demo.model.Imovel;
import com.example.demo.model.Usuario;

import java.util.List;

public record UsuarioDTO(Long id, String nome, String email, String telefone, Categoria categoria, List<Imovel> imoveis) {
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), usuario.getCategoria(), usuario.getImoveis());

    }
}
