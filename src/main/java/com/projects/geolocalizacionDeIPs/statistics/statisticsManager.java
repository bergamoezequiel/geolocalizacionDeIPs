package com.projects.geolocalizacionDeIPs.statistics;

import java.io.IOException;

public class statisticsManager {

	public static StatisticPersister stPersister;
	static statisticsPOJO stPOJOsm = null;

	public static void setStatisticPersister(StatisticPersister stPersisterNew) {
		stPersister = stPersisterNew;
	};

	public static void stdoutPrint(String[] masLejano, String[] masCercano) {
		System.out.println("(PAIS,DISTANCIA,INVOCACIONES)");
		System.out.println("(" + masLejano[0] + "," + masLejano[1] + "," + masLejano[2] + ")");
		System.out.println("(" + masCercano[0] + "," + masCercano[1] + "," + masCercano[2] + ")");

	}

	public static void show() {
		if (stPersister.isEmpty() == false) {
			stdoutPrint(stPersister.fetchData().getFar(), stPersister.fetchData().getNear());
		}
		else {
			System.out.println("No Existe estadistica aun");
		}
	}

	public static void fetchData() {

		stPOJOsm = stPersister.fetchData();

	}

	public static void initializeStatistics(String countryName, int distanceBSAS) {
		String[] masLejano = new String[3];
		String[] masCercano = new String[3];
		masLejano[0] = countryName;
		masCercano[0] = countryName;
		masLejano[1] = String.valueOf(distanceBSAS);
		masCercano[1] = String.valueOf(distanceBSAS);
		masLejano[2] = "1";
		masCercano[2] = "1";
		statisticsPOJO stPOJO = new statisticsPOJO();
		stPOJO.setFar(masLejano);
		stPOJO.setNear(masCercano);
		stPersister.saveStatistics(stPOJO);

	}

	public static void analize(String CountryName, int distanceBSAS) {
		try {
			statisticsPOJO stPOJO = new statisticsPOJO();
			stPOJO.setFar(saveStatisticsLongerDistance(CountryName, distanceBSAS));
			stPOJO.setNear(saveStatisticsShorterDistance(CountryName, distanceBSAS));
			stPersister.saveStatistics(stPOJO);

		} catch (Exception e) {
		}
	}

	private static String[] saveStatisticsLongerDistance(String CountryName, int distanceBSAS) throws IOException {
		String[] masLejano = new String[3];

		if (Integer.parseInt(stPOJOsm.getFar()[1]) < distanceBSAS) {
			masLejano[0] = CountryName;
			masLejano[1] = String.valueOf(distanceBSAS);
			masLejano[2] = String.valueOf(1);
		} else {
			// String.co
			if (Integer.parseInt(stPOJOsm.getFar()[1]) == distanceBSAS && stPOJOsm.getFar()[0].equals(CountryName)) {
				masLejano[0] = CountryName;
				masLejano[1] = String.valueOf(distanceBSAS);
				masLejano[2] = String.valueOf(Integer.valueOf(stPOJOsm.getFar()[2]) + 1);
			} else {
				masLejano[0] = stPOJOsm.getFar()[0];
				masLejano[1] = stPOJOsm.getFar()[1];
				masLejano[2] = stPOJOsm.getFar()[2];

			}

		}
		return masLejano;
	}

	private static String[] saveStatisticsShorterDistance(String CountryName, int distanceBSAS) throws IOException {
		String[] masCercano = new String[3];
		if (Integer.parseInt(stPOJOsm.getNear()[1]) > distanceBSAS) {
			masCercano[0] = CountryName;
			masCercano[1] = String.valueOf(distanceBSAS);
			masCercano[2] = String.valueOf(1);
		} else {
			if (Integer.parseInt(stPOJOsm.getNear()[1]) == distanceBSAS && stPOJOsm.getNear()[0].equals(CountryName)) {
				System.out.println("entra aca");
				masCercano[0] = CountryName;
				masCercano[1] = String.valueOf(distanceBSAS);
				masCercano[2] = String.valueOf(Integer.valueOf(stPOJOsm.getNear()[2]) + 1);

			} else {
				masCercano[0] = stPOJOsm.getNear()[0];
				masCercano[1] = stPOJOsm.getNear()[1];
				masCercano[2] = stPOJOsm.getNear()[2];
			}

		}
		return masCercano;
	}

	public static void updateStatistics(String CountryName, int distanceBSAS) {
		try {
			if (stPersister.isEmpty() == true) {
				initializeStatistics(CountryName, distanceBSAS);
			} else {
				fetchData();
				analize(CountryName, distanceBSAS);
			}

		}

		/*
		 * String toWrite = countryNameDiscoverer.get3lettersCode() +"\r\n";
		 * br.append(countryNameDiscoverer.get3lettersCode()+System.getProperty(
		 * "line.separator"));
		 */

		catch (Exception e) {
		}
		;
	}

}
