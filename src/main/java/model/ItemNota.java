package model;

public class ItemNota {
	protected int idMaterial;
	protected int quantidade;
	protected double valorUnitario;

	public ItemNota(int id, int quantidade, double valorUnitariol) {
		this.idMaterial = id;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
	}

	public ItemNota() {
		
	}

	public int getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(int idMaterial) {
		this.idMaterial = idMaterial;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}


	public void setPrecoUnitario(double preco) {
	}
}
