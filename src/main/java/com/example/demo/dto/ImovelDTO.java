package com.example.demo.dto;

import com.example.demo.model.Imovel;
import com.example.demo.model.Usuario;

import java.math.BigDecimal;

public record ImovelDTO(Long id, String titulo,
                        String descricao, BigDecimal preco,
                        String endereco, String cidade, String estado,
                        String pais,String tipo, boolean disponivel, Usuario usuario) {

        public ImovelDTO(Imovel imovel){
            this(imovel.getId(), imovel.getTitulo(), imovel.getDescricao(), imovel.getPreco(), imovel.getEndereco(),
                    imovel.getCidade(), imovel.getEstado(), imovel.getPais(), imovel.getTipo(), imovel.isDisponivel(), imovel.getUsuario());
        }

}
