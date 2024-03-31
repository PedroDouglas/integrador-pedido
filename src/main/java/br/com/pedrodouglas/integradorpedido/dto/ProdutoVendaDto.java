package br.com.pedrodouglas.integradorpedido.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoVendaDto {
    private Integer idVenda;
    private Integer idProduto;
    private Integer quantidade;
}
