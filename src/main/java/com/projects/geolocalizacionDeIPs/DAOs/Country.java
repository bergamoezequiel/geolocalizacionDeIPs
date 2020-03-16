package com.projects.geolocalizacionDeIPs.DAOs;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

import org.json.*;

public class Country {

	private final String baseUri = "https://restcountries.eu/rest/v2/alpha/";
	
	private RestTemplate restTemplate;

	private String name = null;

	private double latitude;

	private double longitude;

	private ArrayList<String> languages= null;

	private ArrayList<String> currencies= null;

	private ArrayList<String> timezones =null;

	public Country() {
		this.restTemplate = new RestTemplate();
	}

	public Country(RestTemplate restTemp) {
		this.restTemplate = restTemp;
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<String> getTimeZones() {
		return this.timezones;
	}

	public ArrayList<String> getCurrencies() {
		return this.currencies;
	}

	public ArrayList<String> getlanguagues() {
		return this.languages;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	private void parseLanguages(JSONObject country) {
		languages = new ArrayList<String>();
		JSONArray jArray = country.getJSONArray("languages");
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject object = jArray.getJSONObject(i);
			String nativeName = object.getString("name");
			String ISO639_1 = object.getString("iso639_1");
			languages.add(nativeName + " (" + ISO639_1 + ")");
		}
	}

	private void parseCurrencies(JSONObject country) {
		currencies = new ArrayList<String>();
		currencies = new ArrayList<String>();
		JSONArray jArray = country.getJSONArray("currencies");
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject object = jArray.getJSONObject(i);
			String code = object.getString("code");
			currencies.add(code);
		}
	}

	private void parseTimeZones(JSONObject country) {
		timezones = new ArrayList<String>();
		JSONArray jArray = country.getJSONArray("timezones");
		for (int i = 0; i < jArray.length(); i++) {
			String timezone = jArray.getString(i);
			timezones.add(timezone);
		}
	}

	private void parseCoordinates(JSONObject country) {
		JSONArray jArray = country.getJSONArray("latlng");
		jArray.toString();
		latitude = jArray.getDouble(0);
		longitude = jArray.getDouble(1);
	}

	public void Initialize(String threeLetterscode) throws FailedAccessToCountryInformationException {
		ResponseEntity<String> result;
		
		/*Esta consulta podría cachearse ya que son datos que no cambian con frecuencia.
		 *Posible implementación con memcache */
		result = restTemplate.getForEntity(baseUri + threeLetterscode, String.class);
		if (result.getStatusCode().value() != 200) {
			throw (new FailedAccessToCountryInformationException());
		}
		JSONObject root = new JSONObject(result.getBody());
		this.name = root.getString("name");

		/* Estos llamados podrían paralelizarse para mejorar la eficiencia */
		this.parseLanguages(root);
		this.parseCurrencies(root);
		this.parseTimeZones(root);
		this.parseCoordinates(root);
	}
}
