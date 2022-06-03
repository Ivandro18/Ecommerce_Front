package br.edu.unifacisa.services;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleRestClient {

	private String baseURL;
	private HttpClient client;

	public SimpleRestClient(String baseURL) {
		this.setBaseURL(baseURL);
		client = HttpClient.newHttpClient();
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public HttpResponse doGet(String endpoint, String queryParams, String sessionToken) throws IOException, InterruptedException {
		String uri = this.baseURL + "/" + endpoint;
		if (queryParams != null && !queryParams.isEmpty()) {
			uri += "?" + queryParams;
		}
		Builder requestBuilder = HttpRequest.newBuilder()
		    .uri(URI.create(uri));
		if (sessionToken != null) {
			requestBuilder.setHeader("sessionToken", sessionToken);
		}    
		HttpRequest request = requestBuilder.build();
		HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}	
	
	public HttpResponse doPost(String endpoint, Map<String, String> body, String sessionToken) {
		ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.baseURL + "/" + endpoint))
                    .header("sessionToken", sessionToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

    		HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
    		return response;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	public HttpResponse doPut(String endpoint, Map<String, String> body, String sessionToken) {
		ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.baseURL + "/" + endpoint))
                    .header("sessionToken", sessionToken)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

    		HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
    		System.out.println(response);
    		return response;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	public HttpResponse doDelete(String endpoint, String sessionToken) {
		ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.baseURL + "/" + endpoint))
                    .header("sessionToken", sessionToken)
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

    		HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
    		System.out.println(response);
    		return response;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return null;
		
	}

}