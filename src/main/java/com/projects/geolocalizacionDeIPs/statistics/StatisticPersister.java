package com.projects.geolocalizacionDeIPs.statistics;

public interface StatisticPersister {

	public statisticsPOJO fetchData();
	
	public void saveStatistics(statisticsPOJO stPOJO);
	
	public boolean isEmpty();
}
