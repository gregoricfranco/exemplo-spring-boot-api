package com.example.demo.controller;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }



    @GetMapping("/")
    public List<UsuarioDTO> all(){
        List<UsuarioDTO> usuarios = usuarioService.all()
                .stream()
                .map(UsuarioDTO::new)
                .toList();

        return usuarios;
    }

    @PostMapping("/")
    public Usuario create (@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario(usuarioDTO);
        return usuarioService.create(usuario);
    }

}
