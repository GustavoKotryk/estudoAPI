package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private static final String URL ="jdbc:mysql://localhost:3306/APIsDois";
	private static final String USER = "root";
	private static final String PASSWORD = "1234";

	public static Connection getConnection() throws SQLException{
		try{
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e){
			throw new SQLException("Erro ao conectar com o banco" + e.getMessage(), e);
		}
	}
}
