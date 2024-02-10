package com.example.demo.model;


import com.example.demo.dto.EmailDTO;
import com.example.demo.enuns.StatusEmail;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "emails")
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String mensagem;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;

    private List<String> emails;


    public Email(EmailDTO emailDTO) {
        this.emailFrom = emailDTO.emailFrom();
        this.emailTo = emailDTO.emailTo();
        this.subject = emailDTO.subject();
        this.mensagem = emailDTO.mensagem();
        this.sendDateEmail = emailDTO.senDateEmail();
        this.statusEmail = emailDTO.statusEmail();
        this.emails = emailDTO.emails();
    }
}
