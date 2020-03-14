package com.projects.geolocalizacionDeIPs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import com.projects.geolocalizacionDeIPs.Country;
import org.springframework.web.client.RestTemplate;
import org.json.*;
import net.spy.memcached.MemcachedClient;  

/**
 * Hello world!
 *
 */

/*try {

URL url = new URL("https://api.ip2country.info/ip?83.44.196.93");//your url i.e fetch data from .
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod("GET");
conn.setRequestProperty("Accept", "application/json");
if (conn.getResponseCode() != 200) {
    throw new RuntimeException("Failed : HTTP Error code : "
            + conn.getResponseCode());
}
InputStreamReader in = new InputStreamReader(conn.getInputStream());
BufferedReader br = new BufferedReader(in);
String output;
while ((output = br.readLine()) != null) {
    System.out.println(output);
}
conn.disconnect();

} catch (Exception e) {
System.out.println("Exception in NetClientGet:- " + e);
}*/

public class App 
{
	public static void printCurrencies(Country country) {
		System.out.println("Monedas: ");
		ArrayList<String> currencies=country.getCurrencies();
		for (int i=0;i<currencies.size();i++) {
			System.out.println(currencies.get(i));
			MoneyConverter mConverter= new MoneyConverter();
			
			float Conversor= mConverter.calculateExchange("USD",currencies.get(i));
			System.out.println(Conversor);
			System.out.println(currencies.get(i)+" (1 "+currencies.get(i)+" ="+Conversor +" U$S");
		}
	}
	
	public static void printIdioma(Country country) {
		System.out.print("Idiomas: " );
		ArrayList<String> languagues=country.getlanguagues();
		for (int i=0;i<languagues.size();i++) {
			System.out.println(languagues.get(i));
		}
		
	}
	
	public static void printHora(Country country) {
		System.out.println("Hora: ");
		Instant instant = Instant.now();
		//System.out.println(instant.toString());
		//ZoneOffset zoneOffset = ZoneOffset.of( "+01:00" );
		//OffsetDateTime odt = OffsetDateTime.ofInstant( instant , zoneOffset );
		ArrayList<String> timeZones=country.getTimeZones();
		for (int i=0;i<timeZones.size();i++) {
			if(timeZones.get(i).length()==3) {
				System.out.println("   "+instant.toString().substring(11));
			}else {
			String onlyNumber=timeZones.get(i).substring(3);
			//System.out.println(onlyNumber);
			ZoneOffset zoneOffset = ZoneOffset.of( onlyNumber);
			OffsetDateTime odt = OffsetDateTime.ofInstant( instant , zoneOffset );
			System.out.println("   "+odt.toString().substring(11));
			}
		}
		
	}
	
	public static void printHeaderInformation(String IP) {
		 
	   System.out.println("--------------------");
  	   System.out.println("IP: "+IP);
  	   System.out.println("Fecha actual: "+Instant.now().toString());
		
		
	}
	
	public  static void printCountryNameAndISO(CountryNameDiscoverer countryNameDiscoverer) {
		
		System.out.println("País: " +countryNameDiscoverer.getCountryName());
   	    System.out.println("ISO: "+" ("+countryNameDiscoverer.get3lettersCode()+")");
	}
	
    public static void main( String[] args )
    {
    	
    /*	String IP="83.44.196.93";
    	printHeaderInformation(IP);
    	CountryNameDiscoverer countryNameDiscoverer= new CountryNameDiscoverer();
    	countryNameDiscoverer.initilize(IP);
    	printCountryNameAndISO(countryNameDiscoverer);
       
    	Country country = new Country();
    	country.Initialize(countryNameDiscoverer.get3lettersCode());
    	printHora(country);
    	HaversineFormula hFormula = new HaversineFormula();
    	System.out.println("Distancia a BS AS: "+hFormula.calculateDistance(-64, -34, country.getLongitud(),country.getLatitud())+ "kms");
    	printIdioma(country);
    	printCurrencies(country);*/
    	
    	
    	
    	      
    	    // Connecting to Memcached server on localhost  
    	    MemcachedClient mcc = null;
			try {
				mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
    	    System.out.println("Connection to server sucessfully");  
    	   // mcc.delete("ARS");  
    	    System.out.println("add status:"+mcc.add("ARS", 0,34).isDone());
    	    int val=(int) mcc.get("ARS")+1;
    	    System.out.println(val);  
    	    mcc.delete("ARS");
    	    System.out.println("Get from Cache ARS:"+mcc.get("ARS"));  
    	    System.out.println("add status:"+mcc.add("ARS", 0, val++).isDone());
    	    System.out.println("Get from Cache ARS:"+mcc.get("ARS"));
    	    System.out.println("add status:"+mcc.add("PAISES", 0,"ARS").isDone());
    	    mcc.append("PAISES","-ARS");
    	    System.out.println("Get from Cache PAISES:"+mcc.get("PAISES"));
    	   
    	}  
    			   
   
    	
   
}
