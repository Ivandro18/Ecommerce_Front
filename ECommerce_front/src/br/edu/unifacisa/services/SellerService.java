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

public class SellerService {
	
	private SimpleRestClient client;
	static Gson gson;
	static ArrayList<Product> founderList;
	static Type founderListType;
	static ArrayList<ProductRequest> requests;
	static Type requestsType;
	public SellerService() {
		this.client = new SimpleRestClient("http://localhost:8080");
		SellerService.gson = new Gson();
		SellerService.founderListType = new TypeToken<ArrayList<Product>>(){}.getType();
		SellerService.requestsType = new TypeToken<ArrayList<ProductRequest>>(){}.getType();
	}
	
	public String getListMyProducts(String token) throws IOException, InterruptedException {
		HttpResponse response = client.doGet("seller/getListMyProducts", null, token);
		SellerService.founderList = gson.fromJson(response.body().toString(), founderListType);  
		String txt = "";
		//System.out.println(response.body().toString());
		for (int i = 0; i < founderList.size(); i++) {
			txt += String.format("%d. %s\n\n", i + 1, founderList.get(i).toString());
		}
		return txt;		
	}

	public String addProduct(String name, String price, String quantity, String description, int category, String token) throws IOException, InterruptedException {
		Map<String, String> values = new HashMap<String, String>();
    	values.put("name", name);
    	values.put("price", price);
    	values.put("quantity", quantity);
    	values.put("description", description);
    	values.put("category", AdminService.categories.get(category - 1));

		HttpResponse usersResponse = client.doPost("seller/addProducts", values, token);
		return usersResponse.body().toString();
	}

	public String getMySales(String token) throws IOException, InterruptedException {
		HttpResponse response = client.doGet("client/getMySales", null, token);
		SellerService.requests = gson.fromJson(response.body().toString(), SellerService.requestsType);  
		String txt = "";
		for (int i = 0; i < SellerService.requests.size(); i++) {
			txt += String.format(" %d. Nome: %s  Preço:R$%.2f  Qtd: %d  SubTotal: R$%.2f  Envio: %s\n"
					, i + 1, SellerService.requests.get(i).getProdName(), SellerService.requests.get(i).getPrice()
					, SellerService.requests.get(i).getQuantity(), SellerService.requests.get(i).getSubTotal()
					, SellerService.requests.get(i).getStatus());
		}
		return txt;	
	}
	

}
