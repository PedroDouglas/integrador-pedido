package br.com.pedrodouglas.integradorpedido.controller;

import br.com.pedrodouglas.integradorpedido.dto.ProdutoVendaDto;
import br.com.pedrodouglas.integradorpedido.dto.VendaDto;
import br.com.pedrodouglas.integradorpedido.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {
    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaDto> registrarVenda(@RequestBody VendaDto vendaDto) {
        VendaDto vendaDtoRetorno = vendaService.registrarVenda(vendaDto);
        return new ResponseEntity<>(vendaDtoRetorno, HttpStatus.CREATED);
    }

    @PostMapping("/{idVenda}/adicionarItem")
    public ResponseEntity<VendaDto> adicionarItemAVenda(@PathVariable Integer idVenda, @RequestBody ProdutoVendaDto novoProdutoVendaDto) {
        VendaDto vendaDto = vendaService.adicionarItemAVenda(idVenda, novoProdutoVendaDto);
        return new ResponseEntity<>(vendaDto, HttpStatus.OK);
    }

    @DeleteMapping("/{vendaId}/removerItem/{produtoId}")
    public ResponseEntity<String> removerItemDaVenda(@PathVariable int vendaId, @PathVariable int produtoId) {
        vendaService.removerItemDaVenda(vendaId, produtoId);
        return new ResponseEntity<>("Item removido com sucesso da venda.", HttpStatus.OK);
    }
}
