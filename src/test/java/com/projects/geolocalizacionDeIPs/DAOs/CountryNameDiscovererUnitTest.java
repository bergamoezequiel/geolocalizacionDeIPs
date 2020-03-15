package com.projects.geolocalizacionDeIPs.DAOs;


import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.projects.geolocalizacionDeIPs.DAOs.CountryNameDiscoverer;
import com.projects.geolocalizacionDeIPs.DAOs.FailedAccessToCountryNameInformationException;

@RunWith(MockitoJUnitRunner.class)
public class CountryNameDiscovererUnitTest {
	
	@Mock
	private RestTemplate restTemplate;
	
	private CountryNameDiscoverer countryNameDiscoverer;
	
	@Before
    public void init() {
		String IP= "5.231.173.139";
		String str="{\"countryCode\":\"AR\",\"countryCode3\":\"ARG\",\"countryName\":\"Argentina\",\"countryEmoji\":\"🇦🇷\"}";    	
		ResponseEntity<String> resp = new ResponseEntity<String>(str, HttpStatus.OK);
		Mockito.when(restTemplate.getForEntity("https://api.ip2country.info/ip?5.231.173.139", String.class))
           .thenReturn(resp);
         countryNameDiscoverer = new CountryNameDiscoverer(restTemplate);
         countryNameDiscoverer.initilize(IP);
    }
	
	
	@Test
	public void shouldHasArgentinaAsCountryName() {
		assertEquals("Argentina",countryNameDiscoverer.getCountryName());	
	}
	
	@Test
	public void shouldHasARGAsCountry3lettersCode() {
		assertEquals("ARG",countryNameDiscoverer.get3lettersCode());
	}
	
	@Test(expected = FailedAccessToCountryNameInformationException.class)
	public void shouldThrownFaultBecauseAPIReturnHTTP504() {
		String IP= "5.231.173.139";
		String str="{\"countryCode\":\"AR\",\"countryCode3\":\"ARG\",\"countryName\":\"Argentina\",\"countryEmoji\":\"🇦🇷\"}";    	
		ResponseEntity<String> resp = new ResponseEntity<String>(str, HttpStatus.GATEWAY_TIMEOUT);
		Mockito.when(restTemplate.getForEntity("https://api.ip2country.info/ip?5.231.173.139", String.class))
           .thenReturn(resp);
          CountryNameDiscoverer countryNameDiscoverer2 = new CountryNameDiscoverer(restTemplate);
         countryNameDiscoverer2.initilize(IP);
	}
	
	
}
