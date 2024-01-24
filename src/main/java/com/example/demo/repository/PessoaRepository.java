package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Pessoa;

import java.util.List;
import java.util.UUID;


public interface PessoaRepository  extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findByNome(String nome);
    List<Pessoa> findByIdade(Integer idade);
}
