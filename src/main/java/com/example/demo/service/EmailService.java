package com.example.demo.service;

import com.example.demo.enuns.Categoria;
import com.example.demo.enuns.StatusEmail;
import com.example.demo.model.Email;
import com.example.demo.model.Usuario;
import com.example.demo.repository.EmailRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class EmailService {
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    private void sendEmail(Email email) {
        email.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(email.getEmailTo());
            simpleMailMessage.setSubject(email.getSubject());
            simpleMailMessage.setText(email.getText());
            emailSender.send(simpleMailMessage);
            email.setStatusEmail(StatusEmail.Enviado);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            email.setStatusEmail(StatusEmail.Erro);
        }finally {
            emailRepository.save(email);
        }
    }

    public void sendEmailCreate(String emailTo, String mensagem, String subject){
        Email email = new Email(fromEmail, emailTo, subject, mensagem);
        this.sendEmail(email);
        this.sendEmailAllAdm();
    }

    private void sendEmailAllAdm (){
        String mensagem = "Novo usuário %s se cadastrou com sucesso!"; // Informative message

        List<String> emailsAdm = usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getCategoria() == Categoria.ADMINISTRADOR)
                .map(Usuario::getEmail)
                .collect(Collectors.toList());

        emailsAdm.forEach(email -> sendEmail(new Email(fromEmail, email, "Ola admin, novo usuario cadastado do sistema", String.format(mensagem, email))));
    }

}
