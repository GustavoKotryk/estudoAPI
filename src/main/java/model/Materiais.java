package model;

public class Materiais {
	protected int id;
	protected String nome;
	protected String unidade;
	protected double estoque;

	public Materiais(int id, String nome, String unidade, double estoque) {
		this.id = id;
		this.nome = nome;
		this.unidade = unidade;
		this.estoque = estoque;
	}

	public Materiais() {

	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public double getEstoque() {
		return estoque;
	}
	public void setEstoque(double estoque) {
		this.estoque = estoque;
	}

}
