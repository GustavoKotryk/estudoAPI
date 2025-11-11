package repository;

import model.Fornecedor;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FornecedorRepository {
	public void save(Fornecedor fornecedor) {
		String sql = "INSERT INTO Fornecedor (nome, cnpj) VALUES (?, ?)";

		try (Connection conn = Conexao.getConnection();
		     PreparedStatement stmt = conn.prepareStatement(sql)){

			stmt.setString(1, fornecedor.getNome());
			stmt.setString(2, fornecedor.getcnpj());

			stmt.execute();

			System.out.println("Fornecedor salvo com sucesso");
		} catch (SQLException e){
			if (e.getErrorCode() == 1062) {
				// O código de erro '1062' é específico do MySQL para "Duplicate entry"
				System.out.print("ERRO: CNPJ já cadastrado");
			}else {
				System.out.println("Erro ao salvar fornecedor " + e.getMessage());
			}
		}

	}

	public List<Fornecedor> findAll() throws SQLException{
		List<Fornecedor> fornecedores = new ArrayList<>();
		String sql = "SELECT * FROM Fornecedor";

		Connection conn = Conexao.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		try{
			conn = Conexao.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next()){
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setId(rs.getInt("id"));
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setcnpj(rs.getString("cnpj"));

				fornecedores.add(fornecedor);
			}
		}catch (SQLException e){
			System.out.println("Erro ao buscar fornecedores " + e.getMessage());
			throw e;
		}finally {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(conn != null) conn.close();
		}
		return fornecedores;
	}
}
