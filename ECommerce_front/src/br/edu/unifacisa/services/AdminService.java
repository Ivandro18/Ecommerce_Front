package br.edu.unifacisa.services;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.json.JSONArray;

public class AdminService {
	private SimpleRestClient client;
	static ArrayList<String> categories;

	public AdminService() {
		this.client = new SimpleRestClient("http://localhost:8080");
		this.categories = new ArrayList<String>();
	}

	public String addCategorie(String name, String token)throws IOException, InterruptedException {
		HttpResponse loginResponse = client.doGet("admin/addCat", "name=" + name, token);
    	return loginResponse.body().toString();
		

	}

	public String getCategories() throws IOException, InterruptedException {
		HttpResponse response = client.doGet("admin/getCats", null, "");
		JSONArray array = new JSONArray(response.body().toString());
		AdminService.categories.clear();
		for (int i = 0; i < array.length(); i++) {
    		AdminService.categories.add(array.getString(i));
    	}
		String txt = "";
		for (int i = 0; i < AdminService.categories.size(); i++) {
			txt += String.format(" %d. %s\n", i + 1, AdminService.categories.get(i));
		}
    	return txt;
	}

	public String getCategory(int select) {
		// TODO Auto-generated method stub
		return AdminService.categories.get(select - 1);
	}

	
}
