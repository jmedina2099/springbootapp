package org.springboot.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
public class CountryTest {

	private final Logger logger = LogManager.getLogger(CountryTest.class);

	@Test
	public void test1CountryConstructorGettersAndSetters() {
		this.logger.info("===> testCountryConstructorGettersAndSetters()");
		Country country = new Country();
		country = new Country(100L, "UN NOMBRE", "UNA DESCRIPCION");
		assertEquals(Long.valueOf(100L), country.getId());
		assertEquals("UN NOMBRE", country.getName());
		assertEquals("UNA DESCRIPCION", country.getDescription());
		country.setId(101L);
		country.setName("UN NOMBRE1");
		country.setDescription("UNA DESCRIPCION1");
		assertEquals(Long.valueOf(101L), country.getId());
		assertEquals("UN NOMBRE1", country.getName());
		assertEquals("UNA DESCRIPCION1", country.getDescription());
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void test2CountryEqualsMethod() {
		this.logger.info("===> testCountryEqualsMethod()");
		Country country1 = new Country(100L, "UN NOMBRE", "UNA DESCRIPCION");
		Country country2 = new Country(100L, "UN NOMBRE", "UNA DESCRIPCIONN");
		Country country3 = new Country(100L, "UN NOMBREE", "UNA DESCRIPCIONN");
		Country country4 = new Country(101L, "UN NOMBREE", "UNA DESCRIPCIONN");
		CountryDto countryDto = new CountryDto("UN NOMBRE", "UNA DESCRIPCION");
		assertEquals(true, country1.equals(country1));
		assertNotEquals(true, country1.equals(country2));
		assertNotEquals(true, country1.equals(country3));
		assertNotEquals(true, country1.equals(country4));
		assertNotEquals(true, country1.equals(countryDto));
		assertNotEquals(true, country1.equals(null));
	}

	@Test
	public void test3CountryHashcodeMethod() {
		this.logger.info("===> testCountryHashcodeMethod()");
		Country country = new Country(100L, "UN NOMBRE", "UNA DESCRIPCION");
		assertEquals(891126984, country.hashCode());
	}

}
