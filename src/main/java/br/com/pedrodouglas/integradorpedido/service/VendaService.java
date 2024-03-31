package br.com.pedrodouglas.integradorpedido.service;

import br.com.pedrodouglas.integradorpedido.dto.ProdutoVendaDto;
import br.com.pedrodouglas.integradorpedido.dto.VendaDto;
import br.com.pedrodouglas.integradorpedido.exception.IntegradorException;
import br.com.pedrodouglas.integradorpedido.model.Produto;
import br.com.pedrodouglas.integradorpedido.model.ProdutoVenda;
import br.com.pedrodouglas.integradorpedido.model.Venda;
import br.com.pedrodouglas.integradorpedido.repository.ProdutoVendaDao;
import br.com.pedrodouglas.integradorpedido.repository.VendaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VendaService {
    @Autowired
    private VendaDao vendaDao;

    @Autowired
    private ProdutoVendaDao produtoVendaDao;

    @Autowired
    private ProdutoService produtoService;

    public VendaDto registrarVenda(VendaDto vendaDto) {
        List<ProdutoVenda> produtosVenda = new ArrayList<>();
        List<ProdutoVendaDto> produtoVendaDtos = new ArrayList<>();
        if (Objects.isNull(vendaDto.getProdutos()) || vendaDto.getProdutos().isEmpty()) {
            throw new IntegradorException("A lista de produtos na venda não pode ser nula ou vazia", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        for (ProdutoVendaDto produtoDto : vendaDto.getProdutos()) {
            if (produtoDto.getQuantidade() <= 0) {
                throw new IntegradorException("A quantidade de cada produto na venda deve ser maior que zero", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ProdutoVendaDto produtoDto : vendaDto.getProdutos()) {
            Produto produto = produtoService.listar(produtoDto.getIdProduto());

            if (Objects.isNull(produto)) {
                throw new IntegradorException("Produto com o ID " + produtoDto.getIdProduto() + " não encontrado", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (produto.getQuantidadeDisponivel() < produtoDto.getQuantidade()) {
                throw new IntegradorException("Quantidade insuficiente do produto " + produto.getNome() + " em estoque", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            valorTotal = valorTotal.add(produto.getValorUnitario().multiply(BigDecimal.valueOf(produtoDto.getQuantidade())));

            produtoService.removerQuantidade(produto.getId(), produtoDto.getQuantidade());
            ProdutoVenda produtoVenda = new ProdutoVenda();
            produtoVenda.setIdproduto(produto.getId());
            produtoVenda.setQuantidade(produtoDto.getQuantidade());
            produtosVenda.add(produtoVenda);

        }

        Venda venda = Venda.builder()
                .cliente(vendaDto.getCliente())
                .valorTotal(valorTotal)
                .produtos(produtosVenda)
                .build();
        venda.setId(vendaDao.registrarVenda(venda));

        for (ProdutoVenda produtoVenda : produtosVenda){
            produtoVenda.setIdvenda(venda.getId());
            produtoVendaDao.adicionar(produtoVenda);
            ProdutoVendaDto produtoVendaDto = ProdutoVendaDto.builder()
                    .idProduto(produtoVenda.getIdproduto())
                    .idVenda(venda.getId())
                    .quantidade(produtoVenda.getQuantidade())
                    .build();
            produtoVendaDtos.add(produtoVendaDto);
        }

        return VendaDto.builder()
                .id(venda.getId())
                .valorTotal(valorTotal)
                .cliente(vendaDto.getCliente())
                .produtos(produtoVendaDtos)
                .build();
    }

    public VendaDto adicionarItemAVenda(Integer idVenda, ProdutoVendaDto novoProdutoVendaDto) {
        if (novoProdutoVendaDto.getQuantidade() <= 0) {
            throw new IntegradorException("A quantidade de cada produto na venda deve ser maior que zero", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Produto produto = produtoService.listar(novoProdutoVendaDto.getIdProduto());
        if (Objects.isNull(produto)) {
            throw new IntegradorException("Produto com o ID " + novoProdutoVendaDto.getIdProduto() + " não encontrado", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Venda venda = vendaDao.listarPorId(idVenda).stream().findFirst().orElse(null);

        if (produto.getQuantidadeDisponivel() < novoProdutoVendaDto.getQuantidade()) {
            throw new IntegradorException("Quantidade insuficiente do produto " + produto.getNome() + " em estoque", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        BigDecimal valorTotal = venda.getValorTotal()
                .add(produto.getValorUnitario().multiply(BigDecimal.valueOf(novoProdutoVendaDto.getQuantidade())));

        vendaDao.atualizarValorTotal(idVenda, valorTotal);
        produtoService.removerQuantidade(produto.getId(), novoProdutoVendaDto.getQuantidade());

        ProdutoVenda produtoVenda = new ProdutoVenda();
        produtoVenda.setIdproduto(produto.getId());
        produtoVenda.setQuantidade(novoProdutoVendaDto.getQuantidade());

        Integer vendaId = venda.getId();
        Integer produtoId = produto.getId();

        produtoVenda.setIdvenda(vendaId);
        produtoVenda.setIdproduto(produtoId);

        produtoVendaDao.adicionar(produtoVenda);

        List<ProdutoVenda> produtosVenda = produtoVendaDao.listarPorIdVenda(vendaId);
        List<ProdutoVendaDto> produtosVendDto = new ArrayList<>();
        for(ProdutoVenda produtoVenda1 : produtosVenda ){
           ProdutoVendaDto produtoVendaDto = ProdutoVendaDto.builder()
                   .idVenda(produtoVenda1.getIdvenda())
                   .idProduto(produtoVenda1.getIdproduto())
                   .quantidade(produtoVenda1.getQuantidade())
                   .build();
            produtosVendDto.add(produtoVendaDto);
        }

        return VendaDto.builder()
                .id(venda.getId())
                .cliente(venda.getCliente())
                .valorTotal(valorTotal)
                .produtos(produtosVendDto)
                .build();
    }

    public VendaDto removerItemDaVenda(Integer idVenda, Integer idProduto) {

        if (Objects.isNull(idVenda)  || idVenda <= 0) {
            throw new IntegradorException("O ID da venda deve ser um número inteiro positivo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (Objects.isNull(idProduto) || idProduto <= 0) {
            throw new IntegradorException("O ID do produto deve ser um número inteiro positivo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Produto produto = produtoService.listar(idProduto);

        ProdutoVenda produtoVenda = produtoVendaDao.listarPorIdProdutoEIdVenda(idProduto,idVenda ).stream().findFirst().orElse(null);
        if (Objects.isNull(produtoVenda)) {
            throw new IntegradorException("Produto com o ID " + idProduto + " não encontrado na venda com o ID " + idVenda, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        produtoService.adicionarQuantidade(produto.getId(), produtoVenda.getQuantidade());

        produtoVendaDao.deletar(produtoVenda.getIdproduto(), produtoVenda.getIdvenda());

        Venda venda = vendaDao.listarPorId(idVenda).stream().findFirst().orElse(null);

        BigDecimal novoValorTotal = venda.getValorTotal()
                .subtract(produto.getValorUnitario().multiply(BigDecimal.valueOf(produtoVenda.getQuantidade())));

        venda.setValorTotal(novoValorTotal);
        vendaDao.atualizarValorTotal(idVenda, novoValorTotal);

        List<ProdutoVenda> produtosVenda = produtoVendaDao.listarPorIdVenda(venda.getId());
        List<ProdutoVendaDto> produtosVendDto = new ArrayList<>();
        for(ProdutoVenda produtoVenda1 : produtosVenda ){
            ProdutoVendaDto produtoVendaDto = ProdutoVendaDto.builder()
                    .idVenda(produtoVenda1.getIdvenda())
                    .idProduto(produtoVenda1.getIdproduto())
                    .quantidade(produtoVenda1.getQuantidade())
                    .build();
            produtosVendDto.add(produtoVendaDto);
        }

        return VendaDto.builder()
                .id(venda.getId())
                .cliente(venda.getCliente())
                .valorTotal(novoValorTotal)
                .produtos(produtosVendDto)
                .build();
    }
}
