package br.edu.unifacisa.services;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.edu.unifacisa.entities.Product;
import br.edu.unifacisa.entities.ProductRequest;

public class ClientService {
	private SimpleRestClient client;
	static Gson gson;
	static ArrayList<Product> products;
	static Type ProductsType;
	static Product selectProduct;
	static ArrayList<ProductRequest> requests;
	static Type requestsType;
	public ClientService() {
		this.client = new SimpleRestClient("http://localhost:8080");
		ClientService.gson = new Gson();
		ClientService.ProductsType = new TypeToken<ArrayList<Product>>(){}.getType();
		ClientService.requestsType = new TypeToken<ArrayList<ProductRequest>>(){}.getType();
	}

	public String listProductsByCategory(int select) throws IOException, InterruptedException {
		HttpResponse response = client.doGet("client/listProductsByCategory/" + (select - 1), null, "");
		ClientService.products = gson.fromJson(response.body().toString(), ClientService.ProductsType);  
		
		String txt = "";
		for (int i = 0; i < ClientService.products.size(); i++) {
			txt += String.format(" %d.%s  Qtd:%d\n"
					, i + 1, ClientService.products.get(i).getName(),ClientService.products.get(i).getQuantity());
		}
		return txt;	
	}

	public String getProduct(int select) {
		ClientService.selectProduct = ClientService.products.get(select - 1);
		return ClientService.products.get(select - 1).toString();
	}

	public String addItenCart(int selectQuantity, String token) throws IOException, InterruptedException{
		Map<String, String> values = new HashMap<String, String>();
		
		values.put("prodId", String.valueOf(String.valueOf(ClientService.selectProduct.getId())));
		values.put("prodName",ClientService.selectProduct.getName());
		values.put("seller", 	String.valueOf(ClientService.selectProduct.getSeller()));
    	values.put("quantity", 	String.valueOf(String.valueOf(selectQuantity)));
    	values.put("price",String.valueOf(ClientService.selectProduct.getPrice()));
		HttpResponse usersResponse = client.doPost("client/addIntenCart", values, token);
		return usersResponse.body().toString();
	}

	public String getMyCart(String token) throws IOException, InterruptedException{
		HttpResponse response = client.doGet("client/listMyCart", null, token);
		ClientService.requests = gson.fromJson(response.body().toString(), ClientService.requestsType); 
		String txt = "";
		if (ClientService.requests.size() == 0) {
			return "Vazio";
		}
		double total = 0.0;
		for (int i = 0; i < ClientService.requests.size(); i++) {
			txt += String.format(" %d. Produto:%s  Preço:R$%.2f  Qtd:%d  subTotal:R$%.2f\n"
					, i + 1, ClientService.requests.get(i).getProdName(), ClientService.requests.get(i).getPrice()
					,ClientService.requests.get(i).getQuantity(),ClientService.requests.get(i).getSubTotal());
			total += ClientService.requests.get(i).getSubTotal();
		}
		txt += String.format("\nTotal:R$%.2f", total);
		
		return txt;
	}

	public String confirmPurchase(int menuL3, String address, String token) throws IOException, InterruptedException{
		Map<String, String> values = new HashMap<String, String>();
		String formPayment = "";
		if 		(menuL3 == 1) formPayment = "boleto";
		else if (menuL3 == 2) formPayment = "PIX";
		else if (menuL3 == 3) formPayment = "cartão de credito";
		
    	values.put("formPayment", formPayment);
    	values.put("address", address);
		HttpResponse usersResponse = client.doPost("client/confirmPurchase", values, token);
		return usersResponse.body().toString();
	}

	public String getMyRequests(String token)  throws IOException, InterruptedException {
		HttpResponse response = client.doGet("client/getMyRequests", null, token);
		ClientService.requests = gson.fromJson(response.body().toString(), ClientService.requestsType);  
		String txt = "";
		if (ClientService.requests.size() == 0) {
			return "Vazio\n";
		}
		for (int i = 0; i < ClientService.requests.size(); i++) {
			txt += String.format(" %d. Nome: %s  Preço:R$%.2f  Qtd: %d  SubTotal: R$%.2f  Envio: %s\n"
					, i + 1, ClientService.requests.get(i).getProdName(), ClientService.requests.get(i).getPrice()
					, ClientService.requests.get(i).getQuantity(), ClientService.requests.get(i).getSubTotal()
					, ClientService.requests.get(i).getStatus());
		}
		return txt;	
	}
}
