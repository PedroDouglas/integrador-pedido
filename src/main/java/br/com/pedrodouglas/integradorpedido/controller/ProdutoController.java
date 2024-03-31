package br.com.pedrodouglas.integradorpedido.controller;

import br.com.pedrodouglas.integradorpedido.dto.ProdutoDto;
import br.com.pedrodouglas.integradorpedido.model.Produto;
import br.com.pedrodouglas.integradorpedido.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ModelMapper modelMapper;
    @PostMapping
    public ResponseEntity<ProdutoDto> cadastrarProduto(@RequestBody ProdutoDto produtoDto) {
        Produto produto = modelMapper.map(produtoDto, Produto.class);
        produtoService.cadastrar(produto);
        return new ResponseEntity<>(produtoDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Object buscarProduto(@PathVariable Integer id) {
        Produto produto = produtoService.listar(id);
        ProdutoDto produtoRetornoDTO = modelMapper.map(produto, ProdutoDto.class);
        return ResponseEntity.ok(produtoRetornoDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarTodos();
        List<ProdutoDto> produtosRetornoDTO = new ArrayList<>();

        for (Produto produto : produtos) {
            ProdutoDto produtoDto = modelMapper.map(produto, ProdutoDto.class);
            produtosRetornoDTO.add(produtoDto);
        }

        return ResponseEntity.ok(produtosRetornoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Integer id) {
        produtoService.excluir(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/adicionar-quantidade")
    public ResponseEntity<Void> adicionarQuantidade(@PathVariable Integer id,
                                                    @RequestParam Integer quantidadeAdicionar) {
        produtoService.adicionarQuantidade(id, quantidadeAdicionar);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/remover-quantidade")
    public ResponseEntity<Void> removerQuantidade(@PathVariable Integer id,
                                                  @RequestParam Integer quantidadeRemover) {
        produtoService.removerQuantidade(id, quantidadeRemover);
        return ResponseEntity.ok().build();
    }
}
