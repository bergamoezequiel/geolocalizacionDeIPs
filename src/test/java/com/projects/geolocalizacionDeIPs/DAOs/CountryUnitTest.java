package com.projects.geolocalizacionDeIPs.DAOs;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.projects.geolocalizacionDeIPs.DAOs.Country;
import com.projects.geolocalizacionDeIPs.DAOs.FailedAccessToCountryInformationException;

@RunWith(MockitoJUnitRunner.class)
public class CountryUnitTest {
	
	@Mock 
    private RestTemplate restTemplate;
	private Country country;
	
	@Before
    public void init() {
		String str="{\"name\":\"Spain\",\"topLevelDomain\":[\".es\"],\"alpha2Code\":\"ES\",\"alpha3Code\":\"ESP\",\"callingCodes\":[\"34\"],\"capital\":\"Madrid\",\"altSpellings\":[\"ES\",\"Kingdom of Spain\",\"Reino de España\"],\"region\":\"Europe\",\"subregion\":\"Southern Europe\",\"population\":46438422,\"latlng\":[13.5,-4.0],\"demonym\":\"Spanish\",\"area\":505992.0,\"gini\":34.7,\"timezones\":[\"UTC\",\"UTC+01:00\"],\"borders\":[\"AND\",\"FRA\",\"GIB\",\"PRT\",\"MAR\"],\"nativeName\":\"España\",\"numericCode\":\"724\",\"currencies\":[{\"code\":\"CUC\",\"name\":\"Cuban convertible peso\",\"symbol\":\"$\"},{\"code\":\"CUP\",\"name\":\"Cuban peso\",\"symbol\":\"$\"}],\"languages\":[{\"iso639_1\":\"es\",\"iso639_2\":\"spa\",\"name\":\"Spanish\",\"nativeName\":\"Español\"},{\"iso639_1\":\"gn\",\"iso639_2\":\"grn\",\"name\":\"Guaraní\",\"nativeName\":\"Avañe'ẽ\"}],\"translations\":{\"de\":\"Spanien\",\"es\":\"España\",\"fr\":\"Espagne\",\"ja\":\"スペイン\",\"it\":\"Spagna\",\"br\":\"Espanha\",\"pt\":\"Espanha\",\"nl\":\"Spanje\",\"hr\":\"Španjolska\",\"fa\":\"اسپانیا\"},\"flag\":\"https://restcountries.eu/data/esp.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"ESP\"}";
    	ResponseEntity<String> resp = new ResponseEntity<String>(str, HttpStatus.OK);
         Mockito.when(restTemplate.getForEntity("https://restcountries.eu/rest/v2/alpha/ARG", String.class))
           .thenReturn(resp);
         country = new Country(restTemplate);
         country.Initialize("ARG");
    }
	
	@Test
    public void shouldHasEspañaAsNativeName()
    {
        assertEquals("España",country.getNativeName());
    }
	
	@Test
    public void shouldHas135AsLatitude()
    {
        assertEquals(13.5,country.getLatitude(),0.00001);
    }
	
	@Test
    public void shouldHasNegative4AsLongitude()
    {
        assertEquals(-4,country.getLongitude(),0.00001);
    }
	
	@Test
    public void shouldHasTwoLenguages()
    {
		ArrayList<String> languages = country.getlanguagues();
        assertEquals(2,languages.size());    
        assertEquals("Español (es)",languages.get(0));
        assertEquals("Avañe'ẽ (gn)",languages.get(1));
    }
	
	@Test
    public void shouldHasTwoCurrencies()
    {
    	ArrayList<String> currencies = country.getCurrencies();
        assertEquals(2,currencies.size());    
        assertEquals("CUC",currencies.get(0));
        assertEquals("CUP",currencies.get(1));
    }
	
	@Test
    public void shouldHasTwoTimeZones()
    {
        assertEquals(2,country.getTimeZones().size());    
        assertEquals("UTC",country.getTimeZones().get(0));
        assertEquals("UTC+01:00",country.getTimeZones().get(1));
    }
	
	@Test(expected = FailedAccessToCountryInformationException.class)
    public void shouldThrownExceptionDuringInitialization()
    { String str="{\"name\":\"Spain\",\"topLevelDomain\":[\".es\"],\"alpha2Code\":\"ES\",\"alpha3Code\":\"ESP\",\"callingCodes\":[\"34\"],\"capital\":\"Madrid\",\"altSpellings\":[\"ES\",\"Kingdom of Spain\",\"Reino de España\"],\"region\":\"Europe\",\"subregion\":\"Southern Europe\",\"population\":46438422,\"latlng\":[40.0,-4.0],\"demonym\":\"Spanish\",\"area\":505992.0,\"gini\":34.7,\"timezones\":[\"UTC\",\"UTC+01:00\"],\"borders\":[\"AND\",\"FRA\",\"GIB\",\"PRT\",\"MAR\"],\"nativeName\":\"España\",\"numericCode\":\"724\",\"currencies\":[{\"code\":\"CUC\",\"name\":\"Cuban convertible peso\",\"symbol\":\"$\"},{\"code\":\"CUP\",\"name\":\"Cuban peso\",\"symbol\":\"$\"}],\"languages\":[{\"iso639_1\":\"es\",\"iso639_2\":\"spa\",\"name\":\"Spanish\",\"nativeName\":\"Español\"},{\"iso639_1\":\"gn\",\"iso639_2\":\"grn\",\"name\":\"Guaraní\",\"nativeName\":\"Avañe'ẽ\"}],\"translations\":{\"de\":\"Spanien\",\"es\":\"España\",\"fr\":\"Espagne\",\"ja\":\"スペイン\",\"it\":\"Spagna\",\"br\":\"Espanha\",\"pt\":\"Espanha\",\"nl\":\"Spanje\",\"hr\":\"Španjolska\",\"fa\":\"اسپانیا\"},\"flag\":\"https://restcountries.eu/data/esp.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"ESP\"}";
    ResponseEntity<String> resp = new ResponseEntity<String>(str, HttpStatus.FORBIDDEN);
    Mockito.when(restTemplate.getForEntity("https://restcountries.eu/rest/v2/alpha/ARG", String.class))
       .thenReturn(resp);
     Country country2 = new Country(restTemplate);
     country2.Initialize("ARG");
    
 
    }

}
