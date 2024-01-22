package com.example.demo.controller;

import com.example.demo.models.Pessoa;
import com.example.demo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping("/pessoas")
    public List<Pessoa> getAll() {
        return pessoaRepository.findAll();
    }

    @GetMapping("/pessoas/{nome}")
    public List<Pessoa> findByNome(@PathVariable String nome) {
        return pessoaRepository.findByNome(nome);
    }

    @GetMapping("/pessoasbyIdade/{idade}")
    public List<Pessoa> findByIdade(@PathVariable Integer idade) {

        List<Pessoa> pessoas = pessoaRepository.findByIdade(idade);

        if (pessoas == null) {
            return new ArrayList<>();
        }

        return pessoas;
    }

}