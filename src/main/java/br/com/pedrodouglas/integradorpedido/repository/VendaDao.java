package br.com.pedrodouglas.integradorpedido.repository;

import br.com.pedrodouglas.integradorpedido.ConnectionFactory;
import br.com.pedrodouglas.integradorpedido.exception.IntegradorException;
import br.com.pedrodouglas.integradorpedido.model.Venda;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class VendaDao {
    private Connection conn;

    public int registrarVenda(Venda venda) {
        String sql = "INSERT INTO venda (cliente, valor_total) VALUES (?, ?)";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        PreparedStatement ps = null;
        int id;

        try {
            conn = connectionFactory.recuperarConexao();
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, venda.getCliente());
            ps.setBigDecimal(2, venda.getValorTotal());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new IntegradorException("Não foi possível obter o ID gerado para a venda");
            }
            this.fecharConexao(conn, ps);
            rs.close();
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
        return id;
    }

    public List<Venda> listar() {
        String sql = "SELECT id, cliente, valor_total FROM venda";
        List<Venda> vendas = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setCliente(rs.getString("cliente"));
                venda.setValorTotal(rs.getBigDecimal("valor_total"));
                vendas.add(venda);
            }
            fecharConexao(conn,ps);
            rs.close();
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
        return vendas;
    }

    public List<Venda> listarPorId(int id) {
        String sql = "SELECT id, cliente, valor_total FROM venda WHERE id = ?";
        List<Venda> listaVendas = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {

            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt(1));
                venda.setCliente(rs.getString(2));
                venda.setValorTotal(rs.getBigDecimal(3));
                listaVendas.add(venda);
            }
            fecharConexao(conn,ps);
            rs.close();
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }

        return listaVendas;
    }
    public void deletar(int id) {
        String sql = "DELETE FROM venda WHERE id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            fecharConexao(conn,ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
    }

    public void atualizarValorTotal(int idVenda, BigDecimal novoValorTotal) {
        String sql = "UPDATE venda SET valor_total = ? WHERE id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, novoValorTotal);
            ps.setInt(2, idVenda);
            ps.executeUpdate();
            fecharConexao(conn, ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
    }

    public void fecharConexao( Connection conn, PreparedStatement ps) {
        try {
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
    }
}
