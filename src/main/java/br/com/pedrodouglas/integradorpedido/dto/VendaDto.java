package br.com.pedrodouglas.integradorpedido.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VendaDto {
    private Integer id;
    private String cliente;
    private BigDecimal valorTotal;
    private List<ProdutoVendaDto> produtos;
}
