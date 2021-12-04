package br.com.javaparaweb.capitulo3.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaMySQL {
	
	public static void main(String[] args) {
		Connection conexao = null;
		
		try {
			String url = "jdbc:mysql://localhost/agenda";
			String usuario = "root";
			String senha = "230102se";
			conexao = DriverManager.getConnection(url, usuario, senha);
			System.out.println("Conectou!");
			conexao.close();
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao criar a conexão com o banco de dados"+ e.getMessage());
		}
	}

}
