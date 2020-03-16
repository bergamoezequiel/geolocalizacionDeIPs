package com.projects.geolocalizacionDeIPs.DAOs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.projects.geolocalizacionDeIPs.DAOs.MoneyConverter;

@RunWith(MockitoJUnitRunner.class)
public class MoneyConverterUnitTest {

	@Mock
	private RestTemplate restTemplate;
	
	private MoneyConverter mConverter;

	@Before
	public void init() {

		String str = "{\"success\":\"true\",\"timestamp\":1584317646,\"base\":\"EUR\",\"date\":\"2020-03-16\",\"rates\":{\"AED\":4.097202,\"AFN\":84.945838,\"ALL\":122.150214,\"AMD\":544.196115,\"ANG\":1.995678,\"AOA\":555.633516,\"ARS\":69.90647,\"AUD\":1.806415,\"AWG\":2.007897,\"AZN\":1.89163,\"BAM\":1.954609,\"BBD\":2.231606,\"BDT\":94.5756,\"BGN\":1.971882,\"BHD\":0.421231,\"BIF\":2113.868933,\"BMD\":1.115498,\"BND\":1.573516,\"BOB\":7.670351,\"BRL\":5.420875,\"BSD\":1.114863,\"BTC\":0.000199,\"BTN\":82.306605,\"BWP\":12.605255,\"BYN\":2.604913,\"BYR\":21863.763111,\"BZD\":2.247418,\"CAD\":1.542935,\"CDF\":1896.346714,\"CHF\":1.05636,\"CLF\":0.033918,\"CLP\":935.902561,\"CNY\":7.817631,\"COP\":4436.32486,\"CRC\":631.599389,\"CUC\":1.115498,\"CUP\":29.5607,\"CVE\":111.15901,\"CZK\":26.275003,\"DJF\":198.246085,\"DKK\":7.473335,\"DOP\":60.124017,\"DZD\":134.44029,\"EGP\":17.447059,\"ERN\":16.732665,\"ETB\":36.197857,\"EUR\":1,\"FJD\":2.507636,\"FKP\":0.900403,\"GBP\":0.900436,\"GEL\":3.112063,\"GGP\":0.900403,\"GHS\":6.180069,\"GIP\":0.900403,\"GMD\":56.55778,\"GNF\":10507.991842,\"GTQ\":8.523562,\"GYD\":233.044858,\"HKD\":8.66046,\"HNL\":27.818923,\"HRK\":7.609974,\"HTG\":105.712176,\"HUF\":338.574312,\"IDR\":16326.292132,\"ILS\":4.093429,\"IMP\":0.900403,\"INR\":82.501144,\"IQD\":1327.44276,\"IRR\":46968.048817,\"ISK\":150.670379,\"JEP\":0.900403,\"JMD\":150.669617,\"JOD\":0.790902,\"JPY\":118.743104,\"KES\":114.778842,\"KGS\":81.300067,\"KHR\":4504.381363,\"KMF\":494.891052,\"KPW\":1003.958513,\"KRW\":1351.750053,\"KWD\":0.343741,\"KYD\":0.929161,\"KZT\":453.509039,\"LAK\":9931.82504,\"LBP\":1698.161602,\"LKR\":205.695956,\"LRD\":220.868912,\"LSL\":18.105051,\"LTL\":3.293776,\"LVL\":0.674754,\"LYD\":1.550553,\"MAD\":10.717149,\"MDL\":19.613302,\"MGA\":4079.377022,\"MKD\":61.629813,\"MMK\":1556.921135,\"MNT\":3085.597283,\"MOP\":8.920867,\"MRO\":398.23317,\"MUR\":42.665429,\"MVR\":17.234396,\"MWK\":822.681041,\"MXN\":24.383255,\"MYR\":4.772658,\"MZN\":73.42175,\"NAD\":18.104318,\"NGN\":407.719439,\"NIO\":38.060655,\"NOK\":11.317967,\"NPR\":131.690811,\"NZD\":1.842859,\"OMR\":0.429306,\"PAB\":1.114963,\"PEN\":3.941891,\"PGK\":3.806637,\"PHP\":57.208289,\"PKR\":175.036658,\"PLN\":4.375346,\"PYG\":7341.342655,\"QAR\":4.061509,\"RON\":4.860571,\"RSD\":118.248358,\"RUB\":81.721949,\"RWF\":1059.723212,\"SAR\":4.19087,\"SBD\":9.226589,\"SCR\":15.292577,\"SDG\":61.714964,\"SEK\":10.797564,\"SGD\":1.577424,\"SHP\":0.900403,\"SLL\":10848.219415,\"SOS\":653.116035,\"SRD\":8.319392,\"STD\":24599.825962,\"SVC\":9.756866,\"SYP\":574.480214,\"SZL\":18.104742,\"THB\":35.474511,\"TJS\":10.808248,\"TMT\":3.904243,\"TND\":3.183626,\"TOP\":2.610381,\"TRY\":7.018469,\"TTD\":7.538428,\"TWD\":33.584862,\"TZS\":2566.516937,\"UAH\":29.154155,\"UGX\":4144.33107,\"USD\":1.115498,\"UYU\":48.418921,\"UZS\":10606.803368,\"VEF\":11.14104,\"VND\":25885.691575,\"VUV\":133.82067,\"WST\":3.07182,\"XAF\":655.594452,\"XAG\":0.075326,\"XAU\":0.000718,\"XCD\":3.014689,\"XDR\":0.804255,\"XOF\":646.989455,\"XPF\":120.334395,\"YER\":279.218236,\"ZAR\":18.152317,\"ZMK\":10040.820355,\"ZMW\":17.782984,\"ZWL\":359.190395}}";
		ResponseEntity<String> resp = new ResponseEntity<String>(str, HttpStatus.OK);
		Mockito.when(restTemplate.getForEntity(
				"http://data.fixer.io/api/latest?access_key=efdb9ec76c7849f67bf7b77bdb873b02", String.class))
				.thenReturn(resp);
		mConverter = new MoneyConverter(restTemplate);
	}

	@Test
	public void validationExchangeBetweenEUREUR() {

		assertEquals(1, mConverter.calculateExchange("EUR", "EUR"), 0);

	}

	@Test
	public void validationExchangeBetweenARSEUR() {

		assertEquals(69.9, mConverter.calculateExchange("ARS", "EUR"), 0.1);

	}

	@Test
	public void validationExchangeBetweenEURUSD() {

		assertEquals(0.88, mConverter.calculateExchange("EUR", "USD"), 0.1);

	}

	@Test
	public void validationExchangeBetweenARSARS() {

		assertEquals(1, mConverter.calculateExchange("ARS", "ARS"), 0);

	}
	
	@Test(expected = FailedAccessToExchangeInformationException.class)
	public void validationFailedAccess() {
		String str = "{\"success\":\"true\",\"timestamp\":1584317646,\"base\":\"EUR\",\"date\":\"2020-03-16\",\"rates\":{\"AED\":4.097202,\"AFN\":84.945838,\"ALL\":122.150214,\"AMD\":544.196115,\"ANG\":1.995678,\"AOA\":555.633516,\"ARS\":69.90647,\"AUD\":1.806415,\"AWG\":2.007897,\"AZN\":1.89163,\"BAM\":1.954609,\"BBD\":2.231606,\"BDT\":94.5756,\"BGN\":1.971882,\"BHD\":0.421231,\"BIF\":2113.868933,\"BMD\":1.115498,\"BND\":1.573516,\"BOB\":7.670351,\"BRL\":5.420875,\"BSD\":1.114863,\"BTC\":0.000199,\"BTN\":82.306605,\"BWP\":12.605255,\"BYN\":2.604913,\"BYR\":21863.763111,\"BZD\":2.247418,\"CAD\":1.542935,\"CDF\":1896.346714,\"CHF\":1.05636,\"CLF\":0.033918,\"CLP\":935.902561,\"CNY\":7.817631,\"COP\":4436.32486,\"CRC\":631.599389,\"CUC\":1.115498,\"CUP\":29.5607,\"CVE\":111.15901,\"CZK\":26.275003,\"DJF\":198.246085,\"DKK\":7.473335,\"DOP\":60.124017,\"DZD\":134.44029,\"EGP\":17.447059,\"ERN\":16.732665,\"ETB\":36.197857,\"EUR\":1,\"FJD\":2.507636,\"FKP\":0.900403,\"GBP\":0.900436,\"GEL\":3.112063,\"GGP\":0.900403,\"GHS\":6.180069,\"GIP\":0.900403,\"GMD\":56.55778,\"GNF\":10507.991842,\"GTQ\":8.523562,\"GYD\":233.044858,\"HKD\":8.66046,\"HNL\":27.818923,\"HRK\":7.609974,\"HTG\":105.712176,\"HUF\":338.574312,\"IDR\":16326.292132,\"ILS\":4.093429,\"IMP\":0.900403,\"INR\":82.501144,\"IQD\":1327.44276,\"IRR\":46968.048817,\"ISK\":150.670379,\"JEP\":0.900403,\"JMD\":150.669617,\"JOD\":0.790902,\"JPY\":118.743104,\"KES\":114.778842,\"KGS\":81.300067,\"KHR\":4504.381363,\"KMF\":494.891052,\"KPW\":1003.958513,\"KRW\":1351.750053,\"KWD\":0.343741,\"KYD\":0.929161,\"KZT\":453.509039,\"LAK\":9931.82504,\"LBP\":1698.161602,\"LKR\":205.695956,\"LRD\":220.868912,\"LSL\":18.105051,\"LTL\":3.293776,\"LVL\":0.674754,\"LYD\":1.550553,\"MAD\":10.717149,\"MDL\":19.613302,\"MGA\":4079.377022,\"MKD\":61.629813,\"MMK\":1556.921135,\"MNT\":3085.597283,\"MOP\":8.920867,\"MRO\":398.23317,\"MUR\":42.665429,\"MVR\":17.234396,\"MWK\":822.681041,\"MXN\":24.383255,\"MYR\":4.772658,\"MZN\":73.42175,\"NAD\":18.104318,\"NGN\":407.719439,\"NIO\":38.060655,\"NOK\":11.317967,\"NPR\":131.690811,\"NZD\":1.842859,\"OMR\":0.429306,\"PAB\":1.114963,\"PEN\":3.941891,\"PGK\":3.806637,\"PHP\":57.208289,\"PKR\":175.036658,\"PLN\":4.375346,\"PYG\":7341.342655,\"QAR\":4.061509,\"RON\":4.860571,\"RSD\":118.248358,\"RUB\":81.721949,\"RWF\":1059.723212,\"SAR\":4.19087,\"SBD\":9.226589,\"SCR\":15.292577,\"SDG\":61.714964,\"SEK\":10.797564,\"SGD\":1.577424,\"SHP\":0.900403,\"SLL\":10848.219415,\"SOS\":653.116035,\"SRD\":8.319392,\"STD\":24599.825962,\"SVC\":9.756866,\"SYP\":574.480214,\"SZL\":18.104742,\"THB\":35.474511,\"TJS\":10.808248,\"TMT\":3.904243,\"TND\":3.183626,\"TOP\":2.610381,\"TRY\":7.018469,\"TTD\":7.538428,\"TWD\":33.584862,\"TZS\":2566.516937,\"UAH\":29.154155,\"UGX\":4144.33107,\"USD\":1.115498,\"UYU\":48.418921,\"UZS\":10606.803368,\"VEF\":11.14104,\"VND\":25885.691575,\"VUV\":133.82067,\"WST\":3.07182,\"XAF\":655.594452,\"XAG\":0.075326,\"XAU\":0.000718,\"XCD\":3.014689,\"XDR\":0.804255,\"XOF\":646.989455,\"XPF\":120.334395,\"YER\":279.218236,\"ZAR\":18.152317,\"ZMK\":10040.820355,\"ZMW\":17.782984,\"ZWL\":359.190395}}";
		ResponseEntity<String> resp = new ResponseEntity<String>(str, HttpStatus.FORBIDDEN);
		Mockito.when(restTemplate.getForEntity(
				"http://data.fixer.io/api/latest?access_key=efdb9ec76c7849f67bf7b77bdb873b02", String.class))
				.thenReturn(resp);
		mConverter = new MoneyConverter(restTemplate);
		assertEquals(1, mConverter.calculateExchange("EUR", "USD"), 0);

	}

}
