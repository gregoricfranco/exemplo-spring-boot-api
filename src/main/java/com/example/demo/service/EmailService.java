package com.example.demo.service;

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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private EmailRepository emailRepository;
    private UsuarioRepository usuarioRepository;
    private JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(EmailRepository emailRepository, UsuarioRepository usuarioRepository, JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailRepository = emailRepository;
        this.usuarioRepository = usuarioRepository;
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Transactional
    private void sendEmail(Email email)  {

        email.setSendDateEmail(LocalDateTime.now());
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(email.getEmailTo());
            mimeMessageHelper.setSubject(email.getSubject());
            Context context = new Context();

            context.setVariable("message", "Welcome to our website! To complete the registration process, please click the following link:");
            context.setVariable("name", "John Doe");
            context.setVariable("validationLink", "http://localhost:8080/validate?token="+ this.createHas());

            String processedString = templateEngine.process("template-email", context);
            mimeMessageHelper.setText(processedString, true);
            emailSender.send(mimeMessage);
            email.setStatusEmail(StatusEmail.Enviado);
        } catch (MessagingException e) {
            email.setStatusEmail(StatusEmail.Erro);
            throw new RuntimeException(e);
        }finally {
            emailRepository.save(email);
        }

    }

    private String createHas(){
        return UUID.randomUUID().toString();
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
