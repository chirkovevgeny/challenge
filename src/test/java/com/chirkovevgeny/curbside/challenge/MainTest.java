package com.chirkovevgeny.curbside.challenge;

import org.testng.annotations.Test;

public class MainTest {
	
	public static String result = "";
	public static String url = "http://challenge.shopcurbside.com/";
	
	
	@Test
	public void runApp(){
		@SuppressWarnings("unused")
		HttpClient client = new HttpClient(url);
    	HttpClient.setSession();
    	System.out.print("Working on it");
    	getLetter("start");
    	System.out.println("\n" + "Secret phrase is: " + result);
	}
	
	 private static void getLetter(String id){
	    	HttpClient client = new HttpClient(url);
	    	client.hitUrl(id);
	    	if(client.getResponse().has("next")){
	    		for (int i=0; i<client.getResponse().getJSONArray("next").length(); i++)
		    		getLetter(client.getResponse().getJSONArray("next").getString(i));
	    	}
	    	else 
	    		result += client.getResponse().getString("secret");
	    }
}
