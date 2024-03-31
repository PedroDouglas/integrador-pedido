package br.com.pedrodouglas.integradorpedido.repository;

import br.com.pedrodouglas.integradorpedido.ConnectionFactory;
import br.com.pedrodouglas.integradorpedido.exception.IntegradorException;
import br.com.pedrodouglas.integradorpedido.model.Produto;
import br.com.pedrodouglas.integradorpedido.model.ProdutoVenda;
import br.com.pedrodouglas.integradorpedido.model.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoVendaDao {
    private Connection conn;
    @Autowired ProdutoDao produtoDao;
    @Autowired VendaDao vendaDao;
    public void adicionar(ProdutoVenda produtoVenda) {
        String sql = "INSERT INTO produto_venda (id_produto, id_venda, quantidade) VALUES (?, ?, ?)";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, produtoVenda.getIdproduto());
            ps.setInt(2, produtoVenda.getIdvenda());
            ps.setInt(3, produtoVenda.getQuantidade());

            ps.execute();
            fecharConexao(conn, ps);

        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
    }

    public List<ProdutoVenda> listarPorIdVenda(int idVenda) {
        String sql = "SELECT id_produto, id_venda, quantidade FROM produto_venda WHERE id_venda = ?";
        List<ProdutoVenda> produtosVenda = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idVenda);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProdutoVenda produtoVenda = new ProdutoVenda();
                produtoVenda.setIdproduto(rs.getInt(1) );
                produtoVenda.setIdvenda(rs.getInt(2));
                produtoVenda.setQuantidade(rs.getInt(3));
                produtosVenda.add(produtoVenda);
            }
            rs.close();
            fecharConexao(conn, ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }

        return produtosVenda;
    }
    public List<ProdutoVenda> listarPorIdProduto(int idProduto) {
        String sql = "SELECT id_produto, id_venda, quantidade FROM produto_venda WHERE id_produto = ?";
        List<ProdutoVenda> produtosVenda = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idProduto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProdutoVenda produtoVenda = new ProdutoVenda();
                produtoVenda.setIdproduto(rs.getInt(1) );
                produtoVenda.setIdvenda(rs.getInt(2));
                produtoVenda.setQuantidade(rs.getInt(3));
                produtosVenda.add(produtoVenda);
            }
            rs.close();
            fecharConexao(conn, ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }

        return produtosVenda;
    }

    public List<ProdutoVenda> listarPorIdProdutoEIdVenda(Integer idProduto, Integer idVenda) {
        String sql = "SELECT id_produto, id_venda, quantidade FROM produto_venda WHERE id_produto = ? AND id_venda = ?";
        List<ProdutoVenda> produtosVenda = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idProduto);
            ps.setInt(2, idVenda);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProdutoVenda produtoVenda = new ProdutoVenda();
                produtoVenda.setIdproduto(rs.getInt(1) );
                produtoVenda.setIdvenda(rs.getInt(2));
                produtoVenda.setQuantidade(rs.getInt(3));
                produtosVenda.add(produtoVenda);
            }
            rs.close();
            fecharConexao(conn, ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }

        return produtosVenda;
    }


    public void atualizarQuantidade(ProdutoVenda produtoVenda) {
        String sql = "UPDATE produto_venda SET quantidade = ? WHERE id_produto = ? AND id_venda = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, produtoVenda.getQuantidade());
            ps.setInt(2, produtoVenda.getIdproduto());
            ps.setInt(3, produtoVenda.getIdvenda());

            ps.execute();
            fecharConexao(conn, ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
    }

    public void deletar(int idProduto, int idVenda) {
        String sql = "DELETE FROM produto_venda WHERE id_produto = ? AND id_venda = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idProduto);
            ps.setInt(2, idVenda);

            ps.execute();
            fecharConexao(conn, ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
    }

    public void fecharConexao(Connection conn, PreparedStatement ps) {
        try {
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
    }
}