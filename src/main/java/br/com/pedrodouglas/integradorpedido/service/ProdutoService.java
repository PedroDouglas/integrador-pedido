package br.com.pedrodouglas.integradorpedido.service;

import br.com.pedrodouglas.integradorpedido.model.Produto;
import br.com.pedrodouglas.integradorpedido.repository.ProdutoDao;
import org.springframework.beans.factory.annotation.Autowired;

public class ProdutoService {
    @Autowired private ProdutoDao produtoDao;
    public void cadastrar(Produto produto){
        if (produto.getQuantidadeDisponivel().compareTo(0) < 0){
            throw new RuntimeException("Não é possível cadastrar produto sem estoque!");
        }
        produtoDao.adicionar(produto);
    }
}
