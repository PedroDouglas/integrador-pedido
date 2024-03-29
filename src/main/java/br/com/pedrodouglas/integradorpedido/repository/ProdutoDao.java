package br.com.pedrodouglas.integradorpedido.repository;

import br.com.pedrodouglas.integradorpedido.ConnectionFactory;
import br.com.pedrodouglas.integradorpedido.model.Produto;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            throw new RuntimeException(e);
        }
    }

    public List<Produto> listarPorId(Integer id){
        String sql = "select id, nome, descricao , quantidade_disponivel, valor_unitario from produto  where id = ?";
        PreparedStatement ps = null;
        ResultSet result = null;
        List<Produto> produtos = null;
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeQuery();

            while(result.next()){

                Produto produto = Produto.builder()
                        .id(result.getInt(1))
                        .nome(result.getString(2))
                        .descricao(result.getString(3))
                        .quantidadeDisponivel(result.getInt(4))
                        .valorUnitario(result.getBigDecimal(5))
                        .build();
                produtos.add(produto);
            }
            fecharConexao(conn,ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  produtos;
    }
    public List<Produto> listar(){
        String sql = "select id, nome, descricao , quantidade_disponivel, valor_unitario from produto ";
        PreparedStatement ps = null;
        ResultSet result = null;
        List<Produto> produtos = null;
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try {
            conn = connectionFactory.recuperarConexao();
            ps = conn.prepareStatement(sql);
            result = ps.executeQuery();

            while(result.next()){

                Produto produto = Produto.builder()
                        .id(result.getInt(1))
                        .nome(result.getString(2))
                        .descricao(result.getString(3))
                        .quantidadeDisponivel(result.getInt(4))
                        .valorUnitario(result.getBigDecimal(5))
                        .build();
                produtos.add(produto);
            }
            fecharConexao(conn,ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }


    }


    public void fecharConexao(Connection conn, PreparedStatement ps){
        try {
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
