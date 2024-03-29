package br.com.pedrodouglas.integradorpedido.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Produto {
    private Integer id;
    private String nome;
    private String descricao;
    private Integer quantidadeDisponivel;
    private BigDecimal valorUnitario;
}
