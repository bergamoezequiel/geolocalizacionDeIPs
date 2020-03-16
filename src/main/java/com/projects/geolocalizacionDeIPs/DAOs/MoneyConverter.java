package com.projects.geolocalizacionDeIPs.DAOs;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MoneyConverter {

	RestTemplate restTemplate;

	private final String URL = "http://data.fixer.io/api/latest?access_key=efdb9ec76c7849f67bf7b77bdb873b02";

	public MoneyConverter() {
		this.restTemplate = new RestTemplate();
	}

	public MoneyConverter(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private JSONObject fetchPriceData() throws FailedAccessToExchangeInformationException {
		ResponseEntity<String> result = restTemplate.getForEntity(URL, String.class);
		if (result.getStatusCode().value() != 200) {
			throw (new FailedAccessToExchangeInformationException());
		}
		JSONObject root = new JSONObject(result.getBody());
		return root.getJSONObject("rates");
	}

	public float calculateExchange(String fromCurrency, String toCurrency) {
		float priceToCurrency;
		if (fromCurrency == toCurrency) {
			return 1;
		}
		JSONObject rates = fetchPriceData();
		priceToCurrency = rates.getFloat(toCurrency);
		float priceFromCurrency = rates.getFloat(fromCurrency);
		return ((1 / priceToCurrency) * priceFromCurrency);

	}

}
