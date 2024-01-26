package com.example.demo.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer idade;

    private String endereco;


    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Pessoa(){

    }
    public Pessoa(String nome, Integer idade, String endereco, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
        this.categoria = categoria;
    }

    public void merge(Pessoa pessoaUpdate) {
        this.nome = pessoaUpdate.getNome();
        this.idade = pessoaUpdate.getIdade();
        this.endereco = pessoaUpdate.getEndereco();
        this.categoria = pessoaUpdate.getCategoria();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}