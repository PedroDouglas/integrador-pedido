package br.com.pedrodouglas.integradorpedido.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProdutoVenda {
    private Venda venda;
    private Produto produto;
    private Integer quantidade;
}
