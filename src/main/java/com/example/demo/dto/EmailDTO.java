package com.example.demo.dto;

import com.example.demo.enuns.StatusEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record EmailDTO(Long id, @NotBlank @Email String emailFrom, @NotBlank @Email String emailTo,
                       String subject, String text, LocalDateTime senDateEmail, StatusEmail statusEmail) {
}
