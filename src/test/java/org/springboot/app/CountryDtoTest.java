package org.springboot.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

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
	public void test1CountryDtoConstructorGettersAndSetters() {
		this.logger.info("===> testCountryDtoConstructorGettersAndSetters()");
		final CountryDto countryDef = new CountryDto();
		assertNull(countryDef.getName());
		assertNull(countryDef.getDescription());
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
	public void test2CountryDtoEqualsMethod() {
		this.logger.info("===> testCountryDtoEqualsMethod()");
		final CountryDto country1 = new CountryDto("UN NOMBRE", "UNA DESCRIPCION");
		final CountryDto country2 = new CountryDto("UN NOMBRE", "UNA DESCRIPCIONN");
		final CountryDto country3 = new CountryDto("UN NOMBREE", "UNA DESCRIPCIONN");
		final CountryDto country4 = new CountryDto("UN NOMBREE", "UNA DESCRIPCIONN");
		final Country country5 = new Country(100L, "UN NOMBRE", "UNA DESCRIPCION");
		assertEquals(true, country1.equals(country1));
		assertNotEquals(true, country1.equals(country2));
		assertNotEquals(true, country1.equals(country3));
		assertNotEquals(true, country1.equals(country4));
		assertNotEquals(true, country1.equals(country5));
		assertNotEquals(true, country1.equals(null));
	}

	@Test
	public void test3CountryDtoHashcodeMethod() {
		this.logger.info("===> testCountryDtoHashcodeMethod()");
		CountryDto country = new CountryDto("UN NOMBRE", "UNA DESCRIPCION");
		assertEquals(891002054, country.hashCode());
	}

}