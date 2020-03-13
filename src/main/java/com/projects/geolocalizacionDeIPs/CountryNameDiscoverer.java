package com.projects.geolocalizacionDeIPs;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

public class CountryNameDiscoverer {
	
	private RestTemplate restTemplate;
	
	private String threeLettersCode = null;
	private String countryName = null;
	
	public CountryNameDiscoverer(RestTemplate restTemplate){
		this.restTemplate=restTemplate;
	}
	
	public CountryNameDiscoverer() {
		this.restTemplate = new RestTemplate();
	}
	
	
	public void initilize(String IP) throws FailedAccessToCountryInformationException {
		final String uri = "https://api.ip2country.info/ip?"+IP;
        ResponseEntity<String> result;
	    result = restTemplate.getForEntity(uri, String.class);
	    if (result.getStatusCode().value()!=200) {
 	    	throw (new FailedAccessToCountryNameInformationException()); 
 	     }
	    JSONObject Respuesta =new JSONObject (result.getBody());    
	    this.threeLettersCode= Respuesta.getString("countryCode3");
		this.countryName = Respuesta.getString("countryName");
	}
	
	public String get3lettersCode() {
		return threeLettersCode;
		
	}
	
	public String getCountryName() {
		return countryName;
		
	}

}
