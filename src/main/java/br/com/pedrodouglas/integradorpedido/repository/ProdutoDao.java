package br.com.pedrodouglas.integradorpedido.repository;

import br.com.pedrodouglas.integradorpedido.ConnectionFactory;
import br.com.pedrodouglas.integradorpedido.exception.IntegradorException;
import br.com.pedrodouglas.integradorpedido.model.Produto;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProdutoDao {
    private Connection conn;
    public void adicionar(Produto produto){
        String sql =  "INSERT INTO produto ( nome, descricao, quantidade_disponivel, valor_unitario)" +
                      "VALUES( ? , ?, ?, ?)" ;
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setInt(3, produto.getQuantidadeDisponivel());
            ps.setBigDecimal(4, produto.getValorUnitario());

            fecharConexao(conn,ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
    }

    public List<Produto> listarPorId(Integer id){
        String sql = "select id, nome, descricao , quantidade_disponivel, valor_unitario from produto  where id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while(rs.next()){

                Produto produto = Produto.builder()
                        .id(rs.getInt(1))
                        .nome(rs.getString(2))
                        .descricao(rs.getString(3))
                        .quantidadeDisponivel(rs.getInt(4))
                        .valorUnitario(rs.getBigDecimal(5))
                        .build();
                produtos.add(produto);
            }
            fecharConexao(conn,ps);
            rs.close();
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        } catch (Exception e){
            throw new IntegradorException(e.getMessage());
        }
        return  produtos;
    }
    public List<Produto> listar(){
        String sql = "select id, nome, descricao , quantidade_disponivel, valor_unitario from produto ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){

                Produto produto = Produto.builder()
                        .id(rs.getInt(1))
                        .nome(rs.getString(2))
                        .descricao(rs.getString(3))
                        .quantidadeDisponivel(rs.getInt(4))
                        .valorUnitario(rs.getBigDecimal(5))
                        .build();
                produtos.add(produto);
            }
            rs.close();
            fecharConexao(conn,ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
        return  produtos;
    }

    public void alterarQuantidadeEstoque(Integer qtdDisponivel,Integer id){
        String sql = "UPDATE produto SET quantidade_disponivel = ? WHERE id = ? ";
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, qtdDisponivel);
            ps.setInt(2, id);
            ps.execute();

            fecharConexao(conn, ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
    }

    public void deletar(Integer id){
        PreparedStatement ps = null;
        String sql = "delete from produto where id = ? ";
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            fecharConexao(conn,ps);
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }


    }


    public void fecharConexao(Connection conn, PreparedStatement ps){
        try {
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new IntegradorException(e.getMessage());
        }
    }


}
