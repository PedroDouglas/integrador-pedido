package br.com.pedrodouglas.integradorpedido.dto;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProdutoDto {

    private Integer id;
    private String nome;
    private String descricao;
    private Integer quantidadeDisponivel;
    private BigDecimal valorUnitario;
}
