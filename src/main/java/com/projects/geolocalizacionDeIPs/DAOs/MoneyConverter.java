package com.projects.geolocalizacionDeIPs.DAOs;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class MoneyConverter  {

	RestTemplate restTemplate;
	
	
	
	public MoneyConverter() {
		this.restTemplate= new RestTemplate();
		
	}
	
	public MoneyConverter(RestTemplate restTemplate) {
		this.restTemplate=restTemplate;
	}
	
	public float calculateExchange(String fromCurrency,String toCurrency) {
		float cotizacionToCurrenci;
	 	   if ( fromCurrency== toCurrency) {
	 		return 1;
	 	 } 
		
		final String uri = "http://127.0.0.1:8080/eze";
         
 	    RestTemplate restTemplate = new RestTemplate();
 	    String result = restTemplate.getForObject(uri, String.class);
 	   // 
 	   JSONObject root= new JSONObject (result);
 	    JSONObject rates=root.getJSONObject("rates");
 	   //System.out.println(rates);
 	  
 	 
 	   cotizacionToCurrenci=rates.getFloat(toCurrency);
 	  
 	  float cotizacionFromCurrency=rates.getFloat(fromCurrency);
 	  // System.out.println(cotizacionFromCurrency);
 	  // System.out.println(cotizacionToCurrenci);
 	  
 	  
 		   return ((1/cotizacionToCurrenci)*cotizacionFromCurrency);
 		   
 	   
	}
	
}
