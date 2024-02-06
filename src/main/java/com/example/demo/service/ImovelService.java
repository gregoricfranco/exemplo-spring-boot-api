package com.example.demo.service;

import com.example.demo.enuns.Categoria;
import com.example.demo.model.Imovel;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ImovelRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImovelService {

    private final ImovelRepository imovelRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;


    @Autowired
    public ImovelService(ImovelRepository imovelRepository, UsuarioRepository usuarioRepository, EmailService emailService) {
        this.imovelRepository = imovelRepository;
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    public List<Imovel> listarTodosImoveis(){
        return imovelRepository.findAll();
    }

    public Imovel cadastrarImovel (Imovel imovel){
        Usuario usuario = usuarioRepository.findById(imovel.getUsuario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        if (!this.isAutorizadoParaCadastro(usuario)) {
            throw new IllegalArgumentException("Usuário não autorizado a criar imóveis");
        }
        this.enviarEmailCadastroImovel(imovel.getUsuario());
        return imovelRepository.save(imovel);
    }


    private boolean isAutorizadoParaCadastro(Usuario usuario) {
        return usuario.getCategoria() == Categoria.ADMINISTRADOR || usuario.getCategoria() == Categoria.CORRETOR;
    }

    private void enviarEmailCadastroImovel(Usuario usuario){
        String mensagem = "Seu imóvel %s foi criado com sucesso!";
        String subject = "Imóvel Criado com Sucesso";
        emailService.sendEmailCreate(usuario.getEmail(), mensagem, subject);

    }

}
