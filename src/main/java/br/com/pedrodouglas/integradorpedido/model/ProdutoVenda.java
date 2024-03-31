package br.com.pedrodouglas.integradorpedido.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProdutoVenda {
    private Integer idvenda;
    private Integer Idproduto;
    private Integer quantidade;
}
