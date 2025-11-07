package database;

import java.sql.Connection;

public class TestConexao {
	public static void main(String[] args) {
		try{
			Connection conn = Conexao.getConnection();
			System.out.println("Conexao bem sucedida");
			conn.close();
		} catch (Exception e){
			System.out.println("Falha na conexao " + e.getMessage());
		}
	}
}
