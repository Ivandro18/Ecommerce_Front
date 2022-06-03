package br.edu.unifacisa.entities;

public class ProductRequest {
	
	private int 	prodId;
	private String 	prodName;
	private String 	seller;
	private String 	status;
	private int		quantity;
	private double 	price;
	
	
	public ProductRequest(int prodId, String prodName, String seller, String status, int quantity, double price) {
		super();
		this.prodId = prodId;
		this.prodName = prodName;
		this.seller = seller;
		this.status = status;
		this.quantity = quantity;
		this.price = price;
	}
	public double getSubTotal() {
		return this.quantity * this.price;
	}
	public int getProdId() {
		return prodId;
	}
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ProductRequest [prodId=" + prodId + ", prodName=" + prodName + ", seller=" + seller + ", quantity="
				+ quantity + ", price=" + price + "]";
	}
	

}
