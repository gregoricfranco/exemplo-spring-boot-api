package com.example.demo.controller;

import com.example.demo.dto.PessoaDto;
import com.example.demo.models.Pessoa;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaRepository pessoaRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public PessoaController(PessoaRepository pessoaRepository, CategoriaRepository categoriaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/")
    public List<Pessoa> getAll() {
        return pessoaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pessoa findById(@PathVariable Long id) {
        return pessoaRepository.findById(id).orElseThrow();
    }

    @PostMapping("/")
    public Pessoa create(@RequestBody @Valid PessoaDto pessoaDto){
        return pessoaRepository.save(pessoaDto.toPessoa());
    }

    @PutMapping("/{id}")
    public Pessoa update(@PathVariable Long id, @RequestBody @Valid Pessoa pessoaUpdate) {
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow();

        pessoa.merge(pessoaUpdate);
        return pessoaRepository.save(pessoa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pessoaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nome/{nome}")
    public List<Pessoa> findByNome(@PathVariable String nome) {
        return pessoaRepository.findByNome(nome);
    }

    @GetMapping("/idade/{idade}")
    public List<Pessoa> findByIdade(@PathVariable Integer idade) {
        return pessoaRepository.findByIdade(idade);
    }

}