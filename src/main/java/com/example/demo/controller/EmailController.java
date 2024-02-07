package com.example.demo.controller;

import com.example.demo.dto.EmailDTO;
import com.example.demo.dto.ImovelDTO;
import com.example.demo.model.Email;
import com.example.demo.model.Imovel;
import com.example.demo.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/")
    public void create(@RequestBody EmailDTO emailDTO){
       emailService.genericSendEmail(emailDTO,  "template-generic");
    }


}
