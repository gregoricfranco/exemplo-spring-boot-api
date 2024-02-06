package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;


    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    public List<Usuario> all(){
        return usuarioRepository.findAll();
    }

    public Usuario create(Usuario usuario){
        this.sendEmailCreateUser(usuario);
        return usuarioRepository.save(usuario);
    }


    private void sendEmailCreateUser(Usuario usuario) {
        String mensagem = usuario.getNome() + ", " +" mensagem ";
        String subject = "Seja bem vindo," + usuario.getNome();
        emailService.sendEmailCreate(usuario.getEmail(), mensagem, subject);
    }

}
