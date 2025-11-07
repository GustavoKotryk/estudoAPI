package repository;

import model.Fornecedor;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
