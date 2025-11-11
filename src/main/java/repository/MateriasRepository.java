package repository;

import database.Conexao;
import model.Fornecedor;
import model.Materiais;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public List<Materiais> findAll() throws SQLException{
		List<Materiais> materiais = new ArrayList<>();
		String sql = "SELECT * FROM Material";

		Connection conn = Conexao.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		try{
			conn = Conexao.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next()){
				Materiais material = new Materiais();
				material.setId(rs.getInt("id"));
				material.setNome(rs.getString("nome"));
				material.setUnidade(rs.getString("unidade"));
				material.setEstoque(rs.getDouble("estoque"));

				materiais.add(material);
			}
		} catch (SQLException e){
			System.out.println("Erro ao buscar materiais " + e.getMessage());
			throw e;
		}finally {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(conn != null) conn.close();
		}
		return materiais;
	}
}
