package com.example.demo.repository;

import com.example.demo.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemRepository  extends JpaRepository<Imagem, Long> {
}
