package br.com.javaparaweb.capitulo3.crudjdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ContatoCrudJDBC {
	
	public void salvar (Contato contato) {
		
		Connection conexao = this.geraConexao();
		PreparedStatement insereSt = null;
		String sql = "insert into contato (nome, telefone, email, dt_cad, obs) values (?,?,?,?,?)";
		
		try {
			insereSt = conexao.prepareStatement(sql);
			insereSt.setString(1,contato.getNome());
			insereSt.setString(2, contato.getTelefone());
			insereSt.setString(3,contato.getEmail());
			insereSt.setDate(4,contato.getDataCadastro());
			insereSt.setString(5, contato.getObservacao());
			insereSt.executeUpdate();	
		} catch (SQLException e) {
			
			System.out.println("Erro ao incluir contato." + e.getMessage());
		} finally {
			try {
				insereSt.close();
				conexao.close();
			} catch (Throwable e ) {
				System.out.println("Erro ao fechar opera??es de inser??o." + e.getMessage());
			}
		}
		
	}
	
	public void atualizar (Contato contato) {
		
		Connection conexao = this.geraConexao();
		PreparedStatement atualizaSt = null;
		
		// Aqui n?o atualizamos o campo data de cadastro
				String sql = "update contato set nome=?, telefone=?, email=?, obs=? where codigo=?";
				
				try {
					atualizaSt = conexao.prepareStatement(sql);
					atualizaSt.setString(1, contato.getNome());
					atualizaSt.setString(2, contato.getTelefone());
					atualizaSt.setString(3, contato.getEmail());
					atualizaSt.setString(4, contato.getObservacao());
					atualizaSt.setInt(5, contato.getCodigo());
					atualizaSt.executeUpdate();
				} catch (SQLException e) {
					System.out.println("Erro ao atualizar contato. Mensagem: " + e.getMessage());
				} finally {
					try {
						atualizaSt.close();
						conexao.close();
					} catch (Throwable e) {
						System.out
								.println("Erro ao fechar opera??es de atualiza??o. Mensagem: "
										+ e.getMessage());
					}
				}
			}		
		
	
	public void excluir (Contato contato) {
		
		Connection conexao = this.geraConexao();
		PreparedStatement excluiSt = null;
		
		String sql = "delete from contato where codigo = ?";
		
		try {
			excluiSt = conexao.prepareStatement(sql);
			excluiSt.setInt(1, contato.getCodigo());
			excluiSt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao excluir contato. Mensagem: "
					+ e.getMessage());
		} finally {
			try {
				excluiSt.close();
				conexao.close();
			} catch (Throwable e) {
				System.out
						.println("Erro ao fechar opera??es de exclus?o. Mensagem: "
								+ e.getMessage());
			}
		}
		
	}
	
	public List<Contato> listar() {
		
		Connection conexao = this.geraConexao();
		List<Contato> contatos = new ArrayList<Contato>();
		Statement consulta = null;
		ResultSet resultado = null;
		Contato contato = null;
		String sql = "select * from contato";
		try {
			
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery(sql);
			while (resultado.next()) {
				contato = new Contato();
				contato.setCodigo(resultado.getInt("codigo"));
				contato.setNome(resultado.getString("nome"));
			    contato.setTelefone(resultado.getString("telefone"));
			    contato.setEmail(resultado.getString("email"));
			    contato.setDataCadastro(resultado.getDate("dt_cad"));
			    contato.setObservacao(resultado.getString("obs"));
			    contatos.add(contato);
			}
			
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao buscar c?digo de contato. Menssagem: " + e.getMessage());
			
		} finally {
			try {
				consulta.close();
				resultado.close();
				conexao.close();
			} catch (Throwable e ) {
				
				System.out.println("Erro ao fechar opera??es de consulta. Mensagem: " + e.getMessage());
				
			}
		}
		
		return contatos;
		
	}
	
	public Contato buscaContato(int valor) {
		
		Connection conexao = this.geraConexao();
		PreparedStatement consulta = null;
		ResultSet resultado = null;
		Contato contato = null;

		String sql = "select * from contato where codigo = ?";
		
		try {
			consulta = conexao.prepareStatement(sql);
			consulta.setInt(1, valor);
			resultado = consulta.executeQuery();

			if (resultado.next()) {
				contato = new Contato();
				contato.setCodigo(resultado.getInt("codigo"));
				contato.setNome(resultado.getString("nome"));
				contato.setTelefone(resultado.getString("telefone"));
				contato.setEmail(resultado.getString("email"));
				contato.setDataCadastro(resultado.getDate("dt_cad"));
				contato.setObservacao(resultado.getString("obs"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar c?digo do contato. Mensagem: "
					+ e.getMessage());
		} finally {
			try {
				consulta.close();
				resultado.close();
				conexao.close();
			} catch (Throwable e) {
				System.out
						.println("Erro ao fechar opera??es de consulta. Mensagem: "
								+ e.getMessage());
			}
		}
		return contato;
		
	}
	
	public Connection geraConexao() {
		
		Connection conexao = null;

		try {
			// Registrando a classe JDBC no sistema em tempo de execu??o
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/agenda";
			String usuario = "root";
			String senha = "230102se";
			conexao = DriverManager.getConnection(url, usuario, senha);
		} catch (ClassNotFoundException e) {
			System.out
					.println("Classe n?o encontrada. Erro: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro de SQL. Erro: "
					+ e.getMessage());
		}
		return conexao;
		
	}
	
	public static void main(String[] args) {
		
		ContatoCrudJDBC contatoCRUDJDBC = new ContatoCrudJDBC();
		
		//Primeiro contato
		Contato fabricio = new Contato();
		fabricio.setNome("Fabricio Mariano");
		fabricio.setTelefone("(27) 99862-1943");
		fabricio.setEmail("fabriciomariano@gmail.com");
		fabricio.setDataCadastro(new Date(System.currentTimeMillis()));
		fabricio.setObservacao("Teste");
		contatoCRUDJDBC.salvar(fabricio);
		
		Contato gladis = new Contato();
		gladis.setNome("Gladislayne Mariano");
		gladis.setTelefone("(27) 99849-4612");
		gladis.setEmail("gladislaynes@gmail.com");
		gladis.setDataCadastro(new Date(System.currentTimeMillis()));
		gladis.setObservacao("Segundo cadastro de teste");
		contatoCRUDJDBC.salvar(gladis);
		
	}
	

}
