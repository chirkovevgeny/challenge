package com.chirkovevgeny.curbside.challenge;

public class App 
{
	public static String result = "";
	public static String url = "http://challenge.shopcurbside.com/";
	
	
    public static void main( String[] args )
    {	
    	@SuppressWarnings("unused")
		HttpClient client = new HttpClient(url);
    	HttpClient.setSession();
    	System.out.print("Working on it");
    	getLetter("start");
    	System.out.println("\n" + result);
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
