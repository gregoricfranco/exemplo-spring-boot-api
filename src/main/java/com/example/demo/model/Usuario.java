    package com.example.demo.model;

    import com.example.demo.dto.UsuarioDTO;
    import com.example.demo.enuns.Categoria;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.*;

    import java.util.List;
    import java.util.UUID;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of = "id")
    @Entity(name = "usuarios")
    @Table(name = "usuarios")
    public class Usuario  {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nome;
        private String telefone;
        private boolean isValidado;

        @Column(unique = true)
        private String email;

        @Transient
        private String validationToken;

        @Enumerated(EnumType.STRING)
        private Categoria categoria;

        @ManyToOne
        private Imagem imagem;

        @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnore
        private List<Imovel> imoveis ;

        public Usuario(UsuarioDTO usuarioDTO){
            this.id = usuarioDTO.id();
            this.nome = usuarioDTO.nome();
            this.email = usuarioDTO.email();
            this.telefone = usuarioDTO.telefone();
            this.categoria = usuarioDTO.categoria();
            this.isValidado = usuarioDTO.isValidado();
            this.validationToken = generateHash();

        }

        public String generateHash() {
            this.validationToken = UUID.randomUUID().toString();
            return validationToken;
        }




    }
