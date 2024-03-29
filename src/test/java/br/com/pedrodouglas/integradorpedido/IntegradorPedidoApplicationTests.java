package br.com.pedrodouglas.integradorpedido;

import br.com.pedrodouglas.integradorpedido.model.Produto;
import br.com.pedrodouglas.integradorpedido.model.Venda;
import br.com.pedrodouglas.integradorpedido.repository.ProdutoDao;
import br.com.pedrodouglas.integradorpedido.repository.VendaDao;
import br.com.pedrodouglas.integradorpedido.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

@SpringBootTest
class IntegradorPedidoApplicationTests {

	@Test
	void contextLoads() {


//		new ProdutoDao(conn).adicionar(new Produto(1, "celular",
//				"samsung", 1, new BigDecimal(2.00)));
//
//		Connection conn1 = connectionFactory.recuperarConexao();
//		List<Produto> produtos = new ProdutoDao(conn1).listarPorId(1);
//		System.out.println(produtos);

		//new VendaDao(conn).adicionar(new Venda(1, "pedro" , new BigDecimal(500)));
//		Venda venda = new VendaDao(conn).listar().stream().findFirst().get();
//		System.out.println(venda);

//		Venda venda = new VendaDao(conn).listarPorId(1).stream().findFirst().get();
//		System.out.println(venda);

		new ProdutoService().cadastrar((new Produto(1, "celular",
				"samsung", 1, new BigDecimal(2.00))));

	}

}
