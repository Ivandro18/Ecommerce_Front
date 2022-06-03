package br.edu.unifacisa.entities;

public class Product {
	

	private String name;
	private double price;
	private int quantity;
	private String description;
	private String category;
	private String seller;
	private int id;
	
	public Product(String name, double price, int quantity, String description, String category, String seller,int id) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.category = category;
		this.seller = seller;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Nome: %s\n   Preço: $%.2f\n   Quantidade: %d\n   Descrição: %s\n   Categoria: %s\n   Vendedor: %s\n   Id: %d" , 
								this.name, this.price, this.quantity, this.description, this.category, this.seller, this.id);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String categorie) {
		this.category = categorie;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	
}
