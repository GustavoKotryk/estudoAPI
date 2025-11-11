package view;

import model.Fornecedor;
import model.Materiais;
import repository.FornecedorRepository;
import repository.MateriasRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class menuView {
	static Scanner sc = new Scanner(System.in);

	static FornecedorRepository fornecedorRepository = new FornecedorRepository();
	static MateriasRepository materiaisRepository = new MateriasRepository();


	public static void exibir() throws SQLException {
		int opcao = 0;
		do {
			System.out.println("1 - Cadastrar Fornecedor");
			System.out.println("2 - Cadastrar Materiais");
			System.out.println("3 - Registrar Nota De Entrada");

			System.out.println("0 - Sair");
			opcao = sc.nextInt();
			sc.nextLine();

			switch (opcao) {
				case 1:
					cadastrarFornecedor();
					break;
				case 2:
					cadastrarMaterial();
					break;
				case 3:
					registrarNota();
				case 0:
					System.out.println("Saindo...");
					break;

				default:
					System.out.println("Opção inválida");
			}
		} while (opcao != 0);
	}

	private static void cadastrarFornecedor() {
		System.out.println("Cadastrar Fornecedor");
		System.out.println("====================");
		String nome;
		String cnpj;

		while (true) {
			System.out.println("[OBRIGATORIO] Digite o nome do fornecedor: ");
			nome = sc.nextLine();
			if (nome.isBlank()) {
				System.out.println("Esse campo é obrigatório!");
			} else {
				break;
			}
		}

		while (true) {
			System.out.println("[OBRIGATORIO] Digite o CNPJ do fornecedor: ");
			cnpj = sc.nextLine();
			if (cnpj.isBlank()) {
				System.out.println("Esse campo é obrigatório!");
			} else {
				break;
			}
		}

		Fornecedor novoFornecedor = new Fornecedor();
		novoFornecedor.setNome(nome);
		novoFornecedor.setcnpj(cnpj);

		try {
			FornecedorRepository fornecedorRepository = new FornecedorRepository();
			fornecedorRepository.save(novoFornecedor);
		} catch (Exception e) {
			System.out.println("Erro ao salvar fornecedor " + e.getMessage());
		}
	}

	private static void cadastrarMaterial() {
		System.out.println("Cadastrar Materiais");
		System.out.println("====================");

		String nome;


		while (true) {
			System.out.println("[OBRIGATORIO] Digite o nome do material: ");
			nome = sc.nextLine();
			if (nome.isBlank()) {
				System.out.println("Esse é um campo obrigatorio!");
			} else {
				break;
			}
		}


		System.out.println("Digite a unidade de medida do material: ");
		String unidade = sc.nextLine();

		double estoque = 0.0;
		while (true) {
			System.out.println("Digite a quantia inicial de estoque do material: ");
			estoque = sc.nextDouble();
			if (estoque < 0) {
				System.out.println("Estoque deve ser maior ou igual a zero!");
			} else {
				break;
			}
		}

		sc.nextLine();

		Materiais novoMaterial = new Materiais();
		novoMaterial.setNome(nome);
		novoMaterial.setUnidade(unidade);
		novoMaterial.setEstoque(estoque);

		try {
			MateriasRepository materiaisRepository = new MateriasRepository();
			materiaisRepository.save(novoMaterial);
		} catch (Exception e) {
			System.out.println("Erro ao salvar material " + e.getMessage());
		}
	}

	private static void registrarNota() {
		System.out.println("Registrar Nota");
		System.out.println("====================");

		System.out.println("Favor, selecione o fornecedor: ");
		List<Fornecedor> fornecedores;
		try {
			fornecedores = fornecedorRepository.findAll();

			if (fornecedores.isEmpty()) {
				System.out.println("Nenhum fornecedor cadastrado");
				return;
			}
			for (Fornecedor fornecedor : fornecedores) {
				System.out.println(fornecedor.getId() + " - " + fornecedor.getNome());
			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar fornecedores " + e.getMessage());
			return;
		}

		Fornecedor fornecedorEscolhido = null;

		// Loop until a valid supplier is chosen
		while (fornecedorEscolhido == null) {
			System.out.println("Digite o ID do fornecedor: ");
			try {
				int idFornecedor = sc.nextInt();
				sc.nextLine(); // Consume the newline character

				// Reset found supplier for this attempt
				Fornecedor tempFornecedor = null;
				for (Fornecedor f : fornecedores) {
					if (f.getId() == idFornecedor) {
						tempFornecedor = f;
						break; // Found it, stop searching
					}
				}

				// Check if we found a supplier
				if (tempFornecedor != null) {
					fornecedorEscolhido = tempFornecedor; // Success! This will break the while loop
				} else {
					// The input was a number, but not a valid ID
					System.out.println("ID inválido. Por favor, escolha um ID da lista.");
				}

			} catch (java.util.InputMismatchException e) {
				// The input was not a number
				System.out.println("ERRO: Digite apenas números.");
				sc.nextLine(); // Clear the invalid input from the scanner
			}
		} // End of while loop

		System.out.println("\nFornecedor selecionado: " + fornecedorEscolhido.getNome());
		System.out.println("================================");
	}
}