package com.projects.geolocalizacionDeIPs.DAOs;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CountryNameDiscoverer {

	private RestTemplate restTemplate;

	private String threeLettersCode = null;

	private String countryName = null;

	public CountryNameDiscoverer(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public CountryNameDiscoverer() {
		this.restTemplate = new RestTemplate();
	}

	private void parseJson(JSONObject json) {
		this.threeLettersCode = json.getString("countryCode3");
		this.countryName = json.getString("countryName");
	}

	public void initilize(String IP) throws FailedAccessToCountryInformationException {
		final String uri = "https://api.ip2country.info/ip?" + IP;
		ResponseEntity<String> result;
		result = restTemplate.getForEntity(uri, String.class);
		if (result.getStatusCode().value() != 200) {
			throw (new FailedAccessToCountryNameInformationException());
		}
		parseJson(new JSONObject(result.getBody()));
	}

	public String get3lettersCode() {
		return threeLettersCode;

	}

	public String getCountryName() {
		return countryName;

	}

}
