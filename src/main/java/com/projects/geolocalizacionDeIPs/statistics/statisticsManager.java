package com.projects.geolocalizacionDeIPs.statistics;

import java.io.IOException;

public class statisticsManager {

	public static StatisticPersister stPersister;
	static statisticsPOJO stPOJOsm = null;

	public static void setStatisticPersister(StatisticPersister stPersisterNew) {
		stPersister = stPersisterNew;
	};

	public static void stdoutPrint(String[] farest, String[] nearest) {
		System.out.println("(PAIS,DISTANCIA,INVOCACIONES)");
		System.out.println("(" + farest[0] + "," + farest[1] + "," + farest[2] + ")");
		System.out.println("(" + nearest[0] + "," + nearest[1] + "," + nearest[2] + ")");
		double distanceFarest=Integer.valueOf(farest[1])*Integer.valueOf(farest[2]);
		double distanceNearest=Integer.valueOf(nearest[1])*Integer.valueOf(nearest[2]);
		double averageDistance= (distanceFarest+distanceNearest)/(Integer.valueOf(farest[2])+Integer.valueOf(nearest[2]));
		System.out.println("Distancia promedio: "+averageDistance);

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
		String[] farest = new String[3];
		String[] nearest = new String[3];
		farest[0] = countryName;
		nearest[0] = countryName;
		farest[1] = String.valueOf(distanceBSAS);
		nearest[1] = String.valueOf(distanceBSAS);
		farest[2] = "1";
		nearest[2] = "1";
		statisticsPOJO stPOJO = new statisticsPOJO();
		stPOJO.setFar(farest);
		stPOJO.setNear(nearest);
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
		String[] farest = new String[3];

		if (Integer.parseInt(stPOJOsm.getFar()[1]) < distanceBSAS) {
			farest[0] = CountryName;
			farest[1] = String.valueOf(distanceBSAS);
			farest[2] = String.valueOf(1);
		} else {
			// String.co
			if (Integer.parseInt(stPOJOsm.getFar()[1]) == distanceBSAS && stPOJOsm.getFar()[0].equals(CountryName)) {
				farest[0] = CountryName;
				farest[1] = String.valueOf(distanceBSAS);
				farest[2] = String.valueOf(Integer.valueOf(stPOJOsm.getFar()[2]) + 1);
			} else {
				farest[0] = stPOJOsm.getFar()[0];
				farest[1] = stPOJOsm.getFar()[1];
				farest[2] = stPOJOsm.getFar()[2];

			}

		}
		return farest;
	}

	private static String[] saveStatisticsShorterDistance(String CountryName, int distanceBSAS) throws IOException {
		String[] nearest = new String[3];
		if (Integer.parseInt(stPOJOsm.getNear()[1]) > distanceBSAS) {
			nearest[0] = CountryName;
			nearest[1] = String.valueOf(distanceBSAS);
			nearest[2] = String.valueOf(1);
		} else {
			if (Integer.parseInt(stPOJOsm.getNear()[1]) == distanceBSAS && stPOJOsm.getNear()[0].equals(CountryName)) {
				nearest[0] = CountryName;
				nearest[1] = String.valueOf(distanceBSAS);
				nearest[2] = String.valueOf(Integer.valueOf(stPOJOsm.getNear()[2]) + 1);

			} else {
				nearest[0] = stPOJOsm.getNear()[0];
				nearest[1] = stPOJOsm.getNear()[1];
				nearest[2] = stPOJOsm.getNear()[2];
			}

		}
		return nearest;
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
