package com.example.demo.service;

import com.example.demo.dto.EmailDTO;
import com.example.demo.enuns.Categoria;
import com.example.demo.enuns.StatusEmail;
import com.example.demo.model.Email;
import com.example.demo.model.Usuario;
import com.example.demo.repository.EmailRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final EmailRepository emailRepository;
    private final UsuarioRepository usuarioRepository;
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(EmailRepository emailRepository, UsuarioRepository usuarioRepository, JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailRepository = emailRepository;
        this.usuarioRepository = usuarioRepository;
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }
    @Transactional
    private void sendMail(String to, String subject, String template, Map<String, Object> model) {
        Email email = new Email();
        email.setSendDateEmail(LocalDateTime.now());
        email.setEmailFrom(to);
        email.setEmailFrom(fromEmail);

        Context context = new Context();
        context.setVariables(model);
        String content = templateEngine.process(template, context);

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

            message.setTo(to);
            message.setFrom(fromEmail);
            message.setSubject(subject);
            message.setText(content);
            message.setText(content, true);
            emailSender.send(mimeMessage);
        }catch (MessagingException e){
            email.setStatusEmail(StatusEmail.Erro);
            throw new RuntimeException(e);
        }finally {
            emailRepository.save(email);
        }
    }

    public void genericSendEmail(EmailDTO emailDTO, String template){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("titulo", emailDTO.subject());
        properties.put("mensagem", emailDTO.mensagem());

        emailDTO.emails().forEach(email -> sendMail(email,emailDTO.subject(), template, properties));

    }

    public void avisoNovoUsuarioCadastrado(Usuario usuario){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("name", usuario.getNome());
        properties.put("email", usuario.getEmail());
        properties.put("cargo", usuario.getCategoria());

        List<String> emailsAdm = usuarioRepository.findAll().stream()
                .filter(usuario1 -> usuario1.getCategoria() == Categoria.ADMINISTRADOR)
                .map(Usuario::getEmail)
                .collect(Collectors.toList());

        emailsAdm.forEach(email -> sendMail(email,"Novo usuario Cadastrado no sistema", "template-generic", properties));

    }

    public void validateUser(Usuario usuario){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("name", usuario.getNome());
        properties.put("mensagem", "Olá, " + usuario.getNome() + "! Por favor, valide seu cadastro clicando no link abaixo:");
        properties.put("validationLink", "http://localhost:8080/validate?token="+usuario.getValidationToken());

        sendMail(usuario.getEmail(), "Seja bem-vindo(a), " + usuario.getNome(), "template-valid-user", properties);
        avisoNovoUsuarioCadastrado(usuario);
    }



    public void newPassword(Usuario usuario){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("name", usuario.getNome());
        properties.put("mensagem", "Ola, acesse esse link e crie uma nova senha");
        properties.put("validationLink", "http://localhost:8080/newPassword?token="+usuario.getValidationToken());

        sendMail(usuario.getEmail(), "Crie sua nova senha", "template-new-password", properties);
        avisoNovoUsuarioCadastrado(usuario);
    }

}
