package com.projects.geolocalizacionDeIPs;


import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import com.projects.geolocalizacionDeIPs.DAOs.Country;
import com.projects.geolocalizacionDeIPs.DAOs.CountryNameDiscoverer;
import com.projects.geolocalizacionDeIPs.DAOs.MoneyConverter;
import com.projects.geolocalizacionDeIPs.Tools.HaversineFormula;
import com.projects.geolocalizacionDeIPs.statistics.FilePersister;
import com.projects.geolocalizacionDeIPs.statistics.StatisticPersister;
import com.projects.geolocalizacionDeIPs.statistics.statisticsManager;


public class App {
	public static void printCurrencies(Country country) {
		System.out.println("Monedas: ");
		ArrayList<String> currencies = country.getCurrencies();
		for (int i = 0; i < currencies.size(); i++) {
			MoneyConverter mConverter = new MoneyConverter();
			float Conversor = mConverter.calculateExchange("USD", currencies.get(i));
			System.out.println(currencies.get(i) + " ( 1 " + currencies.get(i) + " = " + Conversor + " U$S )");
		}
	}

	public static void printIdioma(Country country) {
		System.out.print("Idiomas: ");
		ArrayList<String> languagues = country.getlanguagues();
		for (int i = 0; i < languagues.size(); i++) {
			System.out.println(languagues.get(i));
		}

	}

	public static void printHora(Country country) {
		System.out.println("Hora: ");
		Instant instant = Instant.now();
		ArrayList<String> timeZones = country.getTimeZones();
		for (int i = 0; i < timeZones.size(); i++) {
			if (timeZones.get(i).length() == 3) {
				System.out.println("   " + instant.toString().substring(11));
			} else {
				String onlyNumber = timeZones.get(i).substring(3);
				ZoneOffset zoneOffset = ZoneOffset.of(onlyNumber);
				OffsetDateTime odt = OffsetDateTime.ofInstant(instant, zoneOffset);
				System.out.println("   " + odt.toString().substring(11));
			}
		}

	}

	public static void printHeaderInformation(String IP) {
		System.out.println("IP: " + IP);
		System.out.println("Fecha actual: " + Instant.now().toString());
	}

	public static void printCountryNameAndISO(CountryNameDiscoverer countryNameDiscoverer) {
		System.out.println("País: " + countryNameDiscoverer.getCountryName());
		System.out.println("ISO: " + " (" + countryNameDiscoverer.get3lettersCode() + ")");
	}

	public static void showInformationByIP(String IP) {
		printHeaderInformation(IP);
		CountryNameDiscoverer countryNameDiscoverer = new CountryNameDiscoverer();
		boolean anErrorOccurredConsultingCountryName = false;
		try {
			countryNameDiscoverer.initilize(IP);

		} catch (Exception e) {
			anErrorOccurredConsultingCountryName = true;
			System.out.println("Hubo un fallo al intentar consultar el pais perteneciente a esa IP");
		}

		if (anErrorOccurredConsultingCountryName == false) {

			boolean anErrorOccurredConsultingCountryInformation = false;
			printCountryNameAndISO(countryNameDiscoverer);
			Country country = new Country();
			try {
				country.Initialize(countryNameDiscoverer.get3lettersCode());

			} catch (Exception e) {
				System.out.println(
						"Hubo un fallo al intentar consultar Informacion perteneciente al pais del origen de la IP");

				anErrorOccurredConsultingCountryInformation = true;
			}

			if (anErrorOccurredConsultingCountryInformation == false) {
				printHora(country);
				HaversineFormula hFormula = new HaversineFormula();
				int distanceBSAS = hFormula.calculateDistance(-64, -34, country.getLongitude(), country.getLatitude());
				printIdioma(country);
				printCurrencies(country);
				StatisticPersister stPersister = new FilePersister("statistics.txt");
				statisticsManager.setStatisticPersister(stPersister);
				statisticsManager.updateStatistics(country.getNativeName(), distanceBSAS);

			}

		}
	}

	public static void main(String[] args) {

		if (args[0].equals("-e")) {
			StatisticPersister stPersister = new FilePersister("statistics.txt");
			statisticsManager.setStatisticPersister(stPersister);
			statisticsManager.show();
		} else {
			showInformationByIP(args[0]);
		}

	}

}
