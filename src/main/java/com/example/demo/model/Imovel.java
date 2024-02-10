package com.example.demo.model;

import com.example.demo.dto.ImovelDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "imoveis")
@Table(name = "imoveis")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private String tipo; // "venda" or "aluguel"

    @Column(nullable = false)
    private boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    private Imagem imagem;

    public Imovel(ImovelDTO imovelDTO){
        this.id = imovelDTO.id();
        this.descricao = imovelDTO.descricao();
        this.preco = imovelDTO.preco();
        this.endereco = imovelDTO.endereco();
        this.titulo = imovelDTO.titulo();
        this.cidade = imovelDTO.cidade();
        this.estado = imovelDTO.estado();
        this.pais = imovelDTO.pais();
        this.tipo = imovelDTO.tipo();
        this.disponivel = imovelDTO.disponivel();
        this.usuario = imovelDTO.usuario();


    }

}
