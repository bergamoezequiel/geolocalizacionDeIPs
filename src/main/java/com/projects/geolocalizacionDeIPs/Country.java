package com.projects.geolocalizacionDeIPs;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import org.springframework.stereotype.Service;

import org.json.*;

@Service
public class Country {
	
	@Autowired 
	private RestTemplate restTemplate;
	
	private String nativeName;
	
	private double latitud;
	
	private double longitud;
	
	private ArrayList<String> languages;
	
	private ArrayList<String> currencies;
	
	private ArrayList<String> timezones;
	
	public Country() {
		this.restTemplate = new RestTemplate();
	}
	
	public Country(RestTemplate restTemp) {
		this.restTemplate=restTemp;
	}
	
	
	public String getNativeName() {
		return this.nativeName;
	}
	public ArrayList<String> getTimeZones(){
		return this.timezones;
	}
	public ArrayList<String> getCurrencies(){
		return this.currencies;
	}
	public ArrayList<String> getlanguagues(){
		return this.languages;
	}
	public double getLatitud() {
		return this.latitud;
	}
	public double getLongitud() {
		return this.longitud;
	}
	
	
	private void parseLanguages(JSONObject country) {
		 languages=new ArrayList<String>();
		JSONArray jArray= country.getJSONArray("languages");
	 	 // System.out.println(jArray.toString());
	 	 for (int i = 0; i < jArray.length(); i++) {
	 		
	 	    JSONObject object = jArray.getJSONObject(i);
	 	   //System.out.println(object.toString());
	 	    String NombreNativo = object.getString("nativeName");
	 	   //System.out.println(NombreNativo);
	 	    String ISO639_1=object.getString("iso639_1");
	 	  // System.out.println(ISO639_1);
	 	   
	 	    languages.add(NombreNativo+" ("+ISO639_1+")"); 
	 	    }
		
	}
	
	private void parseCurrencies(JSONObject country) {
		  currencies= new ArrayList<String>();
		currencies= new ArrayList<String>();
		JSONArray jArray= country.getJSONArray("currencies");
	 	 for (int i = 0; i < jArray.length(); i++) {
	 	    JSONObject object = jArray.getJSONObject(i);
	 	    String code = object.getString("code");
	 	  
	 	    currencies.add(code); 
	 	    }
		
	}
	
	private void parseTimeZones(JSONObject country) {
		 timezones= new ArrayList<String>();
		JSONArray jArray= country.getJSONArray("timezones");
	 	 for (int i = 0; i < jArray.length(); i++) {
	 	    String timezone = jArray.getString(i); 
	 	  
	 	    timezones.add(timezone); 
	 	    }
		
	}
	
	private void parseCoordinates(JSONObject country) {
		JSONArray jArray= country.getJSONArray("latlng");
		jArray.toString();
		latitud = jArray.getDouble(0);
		longitud = jArray.getDouble(1);
		
	}
	
	//@Autowired 
	//private RestTemplate restTemplate;
	
	public void Initialize(String threeLetterscode) throws FailedAccessToCountryInformationException  {
		
		 final String uri = "https://restcountries.eu/rest/v2/alpha/"+threeLetterscode;
		//final String uri = "https://restcountries.eu/rest/v2/alpha/CAN";
		 
		 ResponseEntity<String> result;
 	     result = restTemplate.getForEntity(uri, String.class);
 	  //   int Status = result.getStatusCode().value()
 	     if (result.getStatusCode().value()!=200) {
 	    	throw (new FailedAccessToCountryInformationException()); 
 	     }
 	    //System.out.print(result);
 	    JSONObject root= new JSONObject (result.getBody());
 	   // System.out.println("--laallaal--"+result.getBody());
 	    this.nativeName=root.getString("nativeName");
 	  //  System.out.print(nativeName);
 	  // System.out.println("-----");
 	    this.parseLanguages(root);
 	    this.parseCurrencies(root);
 	    this.parseTimeZones(root);
 	    this.parseCoordinates(root);
 	 /*  System.out.println(languages.toString());
 	   System.out.println(currencies.toString());
 	   System.out.println(timezones.toString());
 	   System.out.println("latitud"+latitud);
 	   System.out.println("longitud"+ longitud);
 	   
 		//Date date = new Date();
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		//dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		//System.out.println("UTC Time is: " + dateFormat.format(date));
		Instant instant = Instant.now();
		System.out.println(instant.toString());
		ZoneOffset zoneOffset = ZoneOffset.of( "+01:00" );
		OffsetDateTime odt = OffsetDateTime.ofInstant( instant , zoneOffset );
		System.out.println(odt.toString());*/
		
	}
}
