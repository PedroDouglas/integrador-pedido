package br.com.pedrodouglas.integradorpedido.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Venda {
    private Integer id;
    private String cliente;
    private BigDecimal valorTotal;
    private List<ProdutoVenda> produtos;

    public Venda(Integer id, String cliente, BigDecimal valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
    }
}
