package view;

import model.Fornecedor;
import model.Materiais;
import repository.FornecedorRepository;
import repository.MateriasRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class menuView {
	static Scanner sc = new Scanner(System.in);

	public static void exibir() throws SQLException {
		int opcao = 0;
		do {
			System.out.println("1 - Cadastrar Fornecedor");
			System.out.println("2 - Cadastrar Materiais");

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
					//registrarNotaExecucao();
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

		while(true) {
			System.out.println("[OBRIGATORIO] Digite o nome do fornecedor: ");
			nome = sc.nextLine();
			if (nome.isBlank()) {
				System.out.println("Esse campo é obrigatório!");
			} else {
				break;
			}
		}

		while(true) {
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

		try{
			FornecedorRepository fornecedorRepository = new FornecedorRepository();
			fornecedorRepository.save(novoFornecedor);
		} catch (Exception e){
			System.out.println("Erro ao salvar fornecedor " + e.getMessage());
		}
	}

	private static void cadastrarMaterial() {
		System.out.println("Cadastrar Materiais");
		System.out.println("====================");

		String nome;


		while(true) {
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
		while(true) {
			System.out.println("Digite a quantia inicial de estoque do material: ");
			estoque = sc.nextDouble();
			if(estoque < 0) {
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

		try{
			MateriasRepository materiaisRepository = new MateriasRepository();
			materiaisRepository.save(novoMaterial);
		} catch (Exception e) {
			System.out.println("Erro ao salvar material " + e.getMessage());
		}
	}
}