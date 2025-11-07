package repository;

import database.Conexao;
import model.Materiais;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MateriasRepository {

	public void save(Materiais materiais) {
		String sql = "INSERT INTO Material (nome, unidade, estoque) VALUES (?, ?, ?)";

		try(
			Connection conn = Conexao.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)){

		stmt.setString(1, materiais.getNome());
		stmt.setString(2, materiais.getUnidade());
		stmt.setDouble(3, materiais.getEstoque());

		stmt.execute();
		System.out.println("Material salvo com sucesso");
	}catch (SQLException e){
			if(e.getErrorCode() == 1062){
				System.out.println("Erro ao salvar materiais");
			}
		}
}
}
