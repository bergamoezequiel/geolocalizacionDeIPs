package com.projects.geolocalizacionDeIPs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HaversineFormulaUnitTest {
	
	
	private HaversineFormula hFormula = new HaversineFormula();
	
	@Test
	public void HaversineFormulaSameCoordinates() {
		assertEquals(0, hFormula.calculateDistance(40,-4 ,40,-4));
	}
	
	@Test
	public void HaversineFormulaSameLongitude() {
		assertEquals(1556, hFormula.calculateDistance(40,-4 ,40,10));
	}
	
	@Test
	public void HaversineFormulaSameLatitude() {
		assertEquals(4658, hFormula.calculateDistance(-2,-4 ,40,-4));
	}
	
	@Test //Desde punto en Noroeste hacia Sureste
	public void HaversineFormulaNOToSE() {
		assertEquals(7570, hFormula.calculateDistance(-20,10 ,30,-40));
	}
	
	@Test //Desde punto en Noroeste hacia Sureste
	public void HaversineFormulaWithDecimal() {
		assertEquals(7986, hFormula.calculateDistance(-24.5,10.6,30.5,-40.2));
	}
	
	@Test(expected = LatitudeOrLongitudeOutOfRangeException.class)
	public void HaversineFormulaLongitudeOutOfRange() {
		 hFormula.calculateDistance(181,10,10,10);
	}
	
	@Test(expected = LatitudeOrLongitudeOutOfRangeException.class)
	public void HaversineFormulaLatitudeOutOfRange() {
		 hFormula.calculateDistance(10,-90.5,10,10);
	}
	
	@Test
	public void HaversineFormulaBonderCase() {
		assertEquals(20015,hFormula.calculateDistance(-180,-90,180,90));
	}


}
