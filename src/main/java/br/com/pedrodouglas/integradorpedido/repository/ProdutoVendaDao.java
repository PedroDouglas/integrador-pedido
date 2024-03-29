package br.com.pedrodouglas.integradorpedido.repository;

import br.com.pedrodouglas.integradorpedido.ConnectionFactory;
import br.com.pedrodouglas.integradorpedido.model.ProdutoVenda;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

            ps.setInt(1, produtoVenda.getProduto().getId());
            ps.setInt(2, produtoVenda.getVenda().getId());
            ps.setInt(3, produtoVenda.getQuantidade());

            ps.execute();
            fecharConexao(conn, ps);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProdutoVenda> listarPorIdVenda(int idVenda) {
        String sql = "SELECT id_produto, id_venda, quantidade FROM produto_venda WHERE id_venda = ?";
        List<ProdutoVenda> listaProdutoVenda = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idVenda);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProdutoVenda produtoVenda = new ProdutoVenda();
                produtoVenda.setProduto(produtoDao.listarPorId(idVenda).stream().findFirst().orElse(null));
                produtoVenda.setVenda(vendaDao.listarPorId(idVenda).stream().findFirst().orElse(null));
                produtoVenda.setQuantidade(rs.getInt(3));
                listaProdutoVenda.add(produtoVenda);
            }

            fecharConexao(conn, ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaProdutoVenda;
    }

    public void atualizarQuantidade(ProdutoVenda produtoVenda) {
        String sql = "UPDATE produto_venda SET quantidade = ? WHERE id_produto = ? AND id_venda = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, produtoVenda.getQuantidade());
            ps.setInt(2, produtoVenda.getProduto().getId());
            ps.setInt(3, produtoVenda.getVenda().getId());

            ps.execute();
            fecharConexao(conn, ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    public void fecharConexao(Connection conn, PreparedStatement ps) {
        try {
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}