package view;

import model.Fornecedor;
import model.ItemNota;
import model.Materiais;
import repository.FornecedorRepository;
import repository.MateriasRepository;

import java.sql.SQLException;
import java.util.ArrayList;
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

		while (fornecedorEscolhido == null) {
			System.out.println("Digite o ID do fornecedor: ");
			try {
				int idFornecedor = sc.nextInt();
				sc.nextLine();

				Fornecedor tempFornecedor = null;
				for (Fornecedor f : fornecedores) {
					if (f.getId() == idFornecedor) {
						tempFornecedor = f;
						break;
					}
				}

				if (tempFornecedor != null) {
					fornecedorEscolhido = tempFornecedor;
				} else {
					System.out.println("ID inválido. Por favor, escolha um ID da lista.");
				}

			} catch (java.util.InputMismatchException e) {
				System.out.println("ERRO: Digite apenas números.");
				sc.nextLine();
			}
		}

		System.out.println("\nFornecedor selecionado: " + fornecedorEscolhido.getNome());
		System.out.println("================================");
		sc.nextLine();

		List<ItemNota> carrinho = new ArrayList<>();
		List<Materiais> listaMateriais;

		try{
			listaMateriais = materiaisRepository.findAll();
			if(listaMateriais.isEmpty()){
				System.out.println("Nenhum material cadastrado");
				return;
			}
			for(Materiais materiais : listaMateriais){
				System.out.println(materiais.getId() + " - " + materiais.getNome());
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar materiais " + e.getMessage());
			return;
		}while (true) {
			System.out.println("\n--- Adicionar Item na Nota ---");

			for (Materiais m : listaMateriais) {
				System.out.println(m.getId() + " - " + m.getNome() + " (Em estoque: " + m.getEstoque() + ")");
			}

			Materiais materialEscolhido = null;
			int idMaterial = 0;
			while (materialEscolhido == null) {
				System.out.println("\nDigite o ID do material:");
				try {
					idMaterial = sc.nextInt();
					sc.nextLine();

					for (Materiais m : listaMateriais) {
						if (m.getId() == idMaterial) {
							materialEscolhido = m;
							break;
						}
					}
					if (materialEscolhido == null) {
						System.out.println("ID inválido. Escolha um ID da lista.");
					}
				} catch (java.util.InputMismatchException e) {
					System.out.println("ERRO: Digite apenas números.");
					sc.nextLine();
				}
			}
			System.out.println("Material selecionado: " + materialEscolhido.getNome());


			int quantidade = 0;
			while (quantidade <= 0) {
				System.out.println("Digite a quantidade:");
				try {
					quantidade = sc.nextInt();
					sc.nextLine();
					if (quantidade <= 0) {
						System.out.println("ERRO: A quantidade deve ser maior que zero.");
					}
				} catch (java.util.InputMismatchException e) {
					System.out.println("ERRO: Digite um número (ex: 10.5).");
					sc.nextLine();
				}
			}

			double preco = -1;
			while (preco < 0) {
				System.out.println("Digite o preço unitário (R$):");
				try {
					preco = sc.nextDouble();
					sc.nextLine();
					if (preco < 0) {
						System.out.println("ERRO: O preço não pode ser negativo.");
					}
				} catch (java.util.InputMismatchException e) {
					System.out.println("ERRO: Digite um número (ex: 19.99).");
					sc.nextLine();
				}
			}

			ItemNota item = new ItemNota();
			item.setIdMaterial(materialEscolhido.getId());
			item.setQuantidade(quantidade);
			item.setPrecoUnitario(preco);

			carrinho.add(item);
			System.out.println("--- Item adicionado ao carrinho! ---");

			System.out.println("\nDeseja adicionar outro item à nota? (s/n)");
			String continuar = sc.nextLine();

			if (!continuar.equalsIgnoreCase("s")) {
				break;
			}
		}



		System.out.println("================================");
		System.out.println("Fechando a nota...");

		if (carrinho.isEmpty()) {
			System.out.println("Nenhum item foi adicionado. Nota cancelada.");
			return;
		}


		try {


			System.out.println("\nNOTA REGISTRADA COM SUCESSO!");
			System.out.println("O estoque dos materiais foi atualizado.");

		} catch (Exception e) {
			System.out.println("ERRO GRAVE AO SALVAR A NOTA NO BANCO:");
			System.out.println(e.getMessage());
		}
	}

	}