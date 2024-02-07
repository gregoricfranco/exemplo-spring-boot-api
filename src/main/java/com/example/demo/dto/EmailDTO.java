package com.example.demo.dto;

import com.example.demo.enuns.StatusEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record EmailDTO(Long id, @NotBlank @Email String emailFrom, @NotBlank @Email String emailTo,
                       String subject, String mensagem, LocalDateTime senDateEmail, StatusEmail statusEmail, List<String> emails) {

}
