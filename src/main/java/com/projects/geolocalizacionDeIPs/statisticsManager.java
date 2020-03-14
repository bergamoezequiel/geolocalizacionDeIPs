package com.projects.geolocalizacionDeIPs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class statisticsManager {

	public static String[] masLejano = null;
	public static String[] masCercano = null;

	public static void stdoutPrint(String[] masLejano, String[] masCercano) {
		System.out.println("PAIS           DISTANCIA          INVOCACIONES");
		System.out.println(masLejano[0] + "          " + masLejano[1] + "              " + masLejano[2]);
		System.out.println(masCercano[0] + "          " + masCercano[1] + "              " + masCercano[2]);

	}

	public static void show() {
		fetchData();
		stdoutPrint(masLejano, masCercano);

	}

	public static void fetchData() {
		File file = new File("append2.txt");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			masLejano = br.readLine().split("-");
			masCercano = br.readLine().split("-");
			br.close();
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stdoutPrint(masLejano, masCercano);

	}

	public static void initializeStatistics(String countryName, int distanceBSAS, File file) {
		try {
			FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);

			System.out.println("entra");

			bw.write(countryName + "-" + distanceBSAS + "-" + 1 + System.getProperty("line.separator"));
			bw.write(countryName + "-" + distanceBSAS + "-" + 1 + System.getProperty("line.separator"));

			bw.close();
			fw.close();
		} catch (Exception e) {
		}
	}

	public static void analize(String CountryName, int distanceBSAS, File file) {
		try {
			FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);
			saveStatisticsLongerDistance(CountryName,distanceBSAS,bw);
			saveStatisticsShorterDistance(CountryName,distanceBSAS,bw);

		
			bw.close();
			fw.close();

		} catch (Exception e) {
		}
	}
	
	private static void saveStatisticsLongerDistance(String CountryName,int distanceBSAS,BufferedWriter bw) throws IOException {
		if (Integer.parseInt(masLejano[1]) < distanceBSAS) {
			bw.write(CountryName + "-" + distanceBSAS + "-" + 1 + System.getProperty("line.separator"));
			System.out.println("entra1");
		} else {
			// String.co
			if (Integer.parseInt(masLejano[1]) == distanceBSAS && masLejano[0].equals(CountryName)) {

				bw.write(CountryName + "-" + distanceBSAS + "-" + (Integer.parseInt(masLejano[2]) + 1)
						+ System.getProperty("line.separator"));

			} else {

				bw.write(masLejano[0] + "-" + masLejano[1] + "-" + masLejano[2]
						+ System.getProperty("line.separator"));

			}

		}
	}
	
	private static void saveStatisticsShorterDistance(String CountryName,int distanceBSAS,BufferedWriter bw) throws IOException{
		if (Integer.parseInt(masCercano[1]) > distanceBSAS) {
			bw.write(CountryName + "-" + distanceBSAS + "-" + 1 + System.getProperty("line.separator"));
		} else {
			if (Integer.parseInt(masCercano[1]) == distanceBSAS && masCercano[0].equals(CountryName)) {

				bw.write(CountryName + "-" + distanceBSAS + "-" + (Integer.parseInt(masCercano[2]) + 1)
						+ System.getProperty("line.separator"));

			} else {
				bw.write(masCercano[0] + "-" + masCercano[1] + "-" + masCercano[2]
						+ System.getProperty("line.separator"));
			}

		}
	}

	public static void updateStatistics(String CountryName, int distanceBSAS) {
		try {
			File file = new File("append2.txt");
			boolean existe = file.exists();

			if (existe == false) {
				initializeStatistics(CountryName, distanceBSAS, file);
			} else {
				fetchData();
				analize(CountryName, distanceBSAS, file);

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
