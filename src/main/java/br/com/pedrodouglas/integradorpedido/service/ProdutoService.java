package br.com.pedrodouglas.integradorpedido.service;

import br.com.pedrodouglas.integradorpedido.exception.IntegradorException;
import br.com.pedrodouglas.integradorpedido.model.Produto;
import br.com.pedrodouglas.integradorpedido.model.ProdutoVenda;
import br.com.pedrodouglas.integradorpedido.repository.ProdutoDao;
import br.com.pedrodouglas.integradorpedido.repository.ProdutoVendaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class ProdutoService {
    @Autowired private ProdutoDao produtoDao;

    @Autowired private ProdutoVendaDao produtoVendaDao;
    public void cadastrar(Produto produto){
        if (produto.getQuantidadeDisponivel().compareTo(0) < 0){
            throw new IntegradorException("Não é possível cadastrar produto sem estoque!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        produtoDao.adicionar(produto);
    }

    public Produto listar(Integer id ){
        if (Objects.isNull(id)){
            throw new IntegradorException("O ID informado não pode ser nullo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return produtoDao.listarPorId(id).stream().findFirst().orElse(null);
    }

    public List<Produto> listarTodos(){
        return produtoDao.listar();
    }

    public void excluir(Integer id){
        if (Objects.isNull(id)){
            throw new RuntimeException("O ID informado não pode ser nullo");
        }
        Integer qtdEstoque = produtoDao.listarPorId(id)
                .stream()
                .findFirst()
                .map(Produto::getQuantidadeDisponivel)
                .orElse(0);

        ProdutoVenda produtoVenda = produtoVendaDao.listarPorIdProduto(id).stream().findFirst().orElse(null);

        if (Objects.nonNull(produtoVenda)){
            throw new IntegradorException("Não é possível deletar produto com venda.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (qtdEstoque > 0) {
            throw new IntegradorException("Não é possível deletar produto com estoque.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        produtoDao.deletar(id);
    }

    public void adicionarQuantidade(Integer id, Integer quantidadeAdicionar) {
        if (Objects.isNull(id)) {
            throw new IntegradorException("O ID do produto não pode ser nulo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (Objects.isNull(quantidadeAdicionar) || quantidadeAdicionar <= 0) {
            throw new IntegradorException("A quantidade a adicionar deve ser um valor positivo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Produto produto = produtoDao.listarPorId(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Integer quantidadeAtual = produto.getQuantidadeDisponivel();

        if (Objects.isNull(quantidadeAtual)) {
            throw new IntegradorException("Quantidade atual do produto é desconhecida", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Integer novaQuantidade = quantidadeAtual + quantidadeAdicionar;

        produtoDao.alterarQuantidadeEstoque(novaQuantidade, id);
    }

    public void removerQuantidade(Integer id, Integer quantidadeRemover) {
        if (Objects.isNull(id)) {
            throw new IntegradorException("O ID do produto não pode ser nulo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (Objects.isNull(quantidadeRemover) || quantidadeRemover <= 0) {
            throw new IntegradorException("A quantidade a remover deve ser um valor positivo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Produto produto = produtoDao.listarPorId(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IntegradorException("Produto não encontrado"));

        Integer quantidadeAtual = produto.getQuantidadeDisponivel();

        if (Objects.isNull(quantidadeAtual)) {
            throw new IntegradorException("Quantidade atual do produto é desconhecida", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (quantidadeAtual < quantidadeRemover) {
            throw new IntegradorException("A quantidade a remover é maior que a quantidade disponível em estoque", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Integer novaQuantidade = quantidadeAtual - quantidadeRemover;

        produtoDao.alterarQuantidadeEstoque(novaQuantidade, id);
    }

}
