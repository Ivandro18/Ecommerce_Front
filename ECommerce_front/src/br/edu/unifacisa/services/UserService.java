package br.edu.unifacisa.services;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class UserService {

	private SimpleRestClient client;

	public UserService() {
		this.client = new SimpleRestClient("http://localhost:8080");
	}
	
	public String login(String login, String senha) throws IOException, InterruptedException {
		HttpResponse loginResponse = client.doGet("user/login", "userName=" + login + "&" + "password=" + senha, "");
    	return loginResponse.body().toString();
	}
	
	public String createUser( String login, String senha, String nome, String tipoDoUsuario) throws IOException, InterruptedException {
		Map<String, String> values = new HashMap<String, String>();
    	//values.put("id", id);
    	values.put("name", nome);
    	values.put("userName", login);
    	values.put("password", senha);
    	values.put("userType", tipoDoUsuario);

		HttpResponse usersResponse = client.doPost("user", values, "");
		return usersResponse.body().toString();

	}

	public String getUserType(String token) throws IOException, InterruptedException {
		HttpResponse loginResponse = client.doGet("user/userType", null, token);
    	return loginResponse.body().toString();
	}
	

	
}
