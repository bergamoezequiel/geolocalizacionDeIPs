package com.projects.geolocalizacionDeIPs.Tools;

public class HaversineFormula {

	public int calculateDistance(double lon1, double lat1,
			double lon2, double lat2) throws LatitudeOrLongitudeOutOfRangeException {

		if (lat1<-90 || lat1>90 || lat2<-90 || lon1<-180 ||  lon1>180 || lon2<-180 || lon2>180) {
			throw (new LatitudeOrLongitudeOutOfRangeException());
		}
		double earthRadius = 6371; // km

		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
		lon2 = Math.toRadians(lon2);

		double dlon = (lon2 - lon1);
		double dlat = (lat2 - lat1);

		double sinlat = Math.sin(dlat / 2);
		double sinlon = Math.sin(dlon / 2);

		double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
		double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

		double distanceInMeters = earthRadius * c ;

		return (int)distanceInMeters;

		}
	
}
