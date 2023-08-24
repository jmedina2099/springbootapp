package org.springboot.app;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springboot.app.dto.CountryDto;
import org.springboot.app.entity.Country;

/**
 * @author jmedina
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountryDtoTest {

	private final Logger logger = LogManager.getLogger(CountryDtoTest.class);

	@Test
	public void testCountryDtoConstructor() {
		this.logger.info("===> testCountryDtoConstructor()");
		final CountryDto countryDef = new CountryDto();
		assertEquals(null, countryDef.getName());
		assertEquals(null, countryDef.getDescription());
		final CountryDto country = new CountryDto("UN NOMBRE", "UNA DESCRIPCION");
		assertEquals("UN NOMBRE", country.getName());
		assertEquals("UNA DESCRIPCION", country.getDescription());
		country.setName("UN NOMBRE1");
		country.setDescription("UNA DESCRIPCION1");
		assertEquals("UN NOMBRE1", country.getName());
		assertEquals("UNA DESCRIPCION1", country.getDescription());
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testCountryDtoEqualsMethod() {
		this.logger.info("===> testCountryDtoEqualsMethod()");
		CountryDto country1 = new CountryDto("UN NOMBRE", "UNA DESCRIPCION");
		CountryDto country2 = new CountryDto("UN NOMBRE", "UNA DESCRIPCIONN");
		CountryDto country3 = new CountryDto("UN NOMBREE", "UNA DESCRIPCIONN");
		CountryDto country4 = new CountryDto("UN NOMBREE", "UNA DESCRIPCIONN");
		Country country5 = new Country(100L, "UN NOMBRE", "UNA DESCRIPCION");
		assertEquals(true, country1.equals(country1));
		assertEquals(false, country1.equals(country2));
		assertEquals(false, country1.equals(country3));
		assertEquals(false, country1.equals(country4));
		assertEquals(false, country1.equals(country5));
		assertEquals(false, country1.equals(null));
	}

	@Test
	public void testCountryDtoHashcodeMethod() {
		this.logger.info("===> testCountryDtoHashcodeMethod()");
		CountryDto country = new CountryDto("UN NOMBRE", "UNA DESCRIPCION");
		assertEquals(891002054, country.hashCode());
	}

}