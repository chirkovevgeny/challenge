package com.chirkovevgeny.curbside.challenge;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class HttpClient {

	private Client client;
	private JSONObject respObj;
	private static WebResource resource;
	private static Integer hitCounter;
	private static String sessionID;
	public String progressMsg = "Collecting data ";
	
	public HttpClient(String url) {
		client = Client.create();
		resource = client.resource(url);
	}
	
	public void hitUrl(String endpoint) {
		if (hitCounter == 10)
			setSession();
        ClientResponse response = resource.path(endpoint).header("session", sessionID).accept("application/json").get(ClientResponse.class);
        hitCounter ++;
        String output = response.getEntity(String.class);
        respObj = normalizeKey(new JSONObject(output));
        System.out.print(" .");
    }
	
	public static void setSession(){
        ClientResponse response = resource.path("get-session").accept("text/html").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        sessionID = output;
        hitCounter = 0;
	}
	
	public JSONObject getResponse(){
		return respObj;
	}
	
	private JSONObject normalizeKey (JSONObject original){
		Iterator<?> keys = original.keys();
		while (keys.hasNext()){
			String key = (String)keys.next();
			if(!key.equals("next")&(key.equalsIgnoreCase("next"))){
				original.put("next", original.get(key));
				original.remove(key);
				break;
			}
		}
		if (original.has("next")){
			if (!(original.get("next") instanceof JSONArray)){
				JSONArray fixedResult = new JSONArray();
				fixedResult.put(original.get("next").toString());
				original.remove("next");
				original.put("next", fixedResult);
			}
		}
			return original;
	}
	
}
