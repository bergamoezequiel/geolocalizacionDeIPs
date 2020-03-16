package com.projects.geolocalizacionDeIPs.statistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FilePersister implements StatisticPersister {

	File file;

	@Override
	public statisticsPOJO fetchData() {
		statisticsPOJO stPOJO = new statisticsPOJO();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			stPOJO.setFar(br.readLine().split("-"));
			stPOJO.setNear(br.readLine().split("-"));
			br.close();
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// stdoutPrint(masLejano, masCercano);

		return stPOJO;
	}

	public FilePersister(String fileName) {
		file = new File(fileName);

	}

	@Override
	public void saveStatistics(statisticsPOJO stPOJO) {
		try {
			FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(stPOJO.getFar()[0] + "-" + stPOJO.getFar()[1] + "-" + stPOJO.getFar()[2]
					+ System.getProperty("line.separator"));
			bw.write(stPOJO.getNear()[0] + "-" + stPOJO.getNear()[1] + "-" + stPOJO.getNear()[2]
					+ System.getProperty("line.separator"));
			bw.close();
			fw.close();
		} catch (Exception e) {
		}

	}

	@Override
	public boolean isEmpty() {
		return (file.exists()==false);
	};

}
