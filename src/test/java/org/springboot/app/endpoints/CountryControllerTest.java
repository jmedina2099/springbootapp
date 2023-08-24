package org.springboot.app.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springboot.app.constants.Constants.MSG_HELLO;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_CREATE;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_DELETE_BY_ID;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_FIND;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_FIND_BY_ID;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_HELLO;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_MODIFY_BY_ID;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springboot.app.AppTestHelper;
import org.springboot.app.dto.CountryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CountryControllerTest extends AppTestHelper {

	private final Logger logger = LogManager.getLogger(CountryControllerTest.class);

	@Autowired
	private TestRestTemplate restTemplate;

	public CountryControllerTest() {
	}

	@Test
	public void testAHello() {
		this.logger.info("===> testHello()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_HELLO;
		ResponseEntity<String> response = null;
		String object = null;
		try {
			response = restTemplate.getForEntity(new URL(url).toString(), String.class);
			object = response.getBody();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(MSG_HELLO, object);
	}

	@Test
	public void testBFindCountries() {
		this.logger.info("===> testFindCountries()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_FIND;
		ResponseEntity<CountryDto[]> response = null;
		CountryDto[] objects = null;
		try {
			response = restTemplate.getForEntity(new URL(url).toString(), CountryDto[].class);
			objects = response.getBody();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(5, objects.length);
		CountryDto user1 = objects[0];
		assertEquals("USA", user1.getName());
		assertEquals("Neighbor at top", user1.getDescription());
		CountryDto user2 = objects[1];
		assertEquals("France", user2.getName());
		assertEquals("Cardif's headquarter", user2.getDescription());
		CountryDto user3 = objects[2];
		assertEquals("Brazil", user3.getName());
		assertEquals("There is too much sun", user3.getDescription());
		CountryDto user4 = objects[3];
		assertEquals("Italy", user4.getName());
		assertEquals("There is mafia overthere", user4.getDescription());
		CountryDto user5 = objects[4];
		assertEquals("Canada", user5.getName());
		assertEquals("So nice", user5.getDescription());
	}

	@Test
	public void testCCreateCountry() {
		this.logger.info("===> testCreateCountry()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_CREATE;
		CountryDto countryDto = new CountryDto();
		countryDto.setName("HOLA");
		countryDto.setDescription("DESCRIPTION");
		ResponseEntity<Boolean> response = null;
		Boolean value = null;
		try {
			response = restTemplate.postForEntity(new URL(url).toString(), countryDto, Boolean.class);
			value = response.getBody();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(value);
		// Double check action
		ResponseEntity<CountryDto> response2 = findById(6L);
		CountryDto countryDto2 = response2.getBody();
		assertEquals(HttpStatus.OK, response2.getStatusCode());
		assertEquals("HOLA", countryDto2.getName());
		assertEquals("DESCRIPTION", countryDto2.getDescription());
	}

	@Test
	public void testDModifyCountryById() {
		this.logger.info("===> testModifyCountryById()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_MODIFY_BY_ID + "1";
		CountryDto countryDto = new CountryDto();
		countryDto.setName("NAME");
		countryDto.setDescription("DESCRIPTION");
		HttpEntity<CountryDto> httpEntity = new HttpEntity<>(countryDto);
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT, httpEntity, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody());
		// Double check action
		ResponseEntity<CountryDto> response2 = findById(1L);
		CountryDto countryDto2 = response2.getBody();
		assertEquals(HttpStatus.OK, response2.getStatusCode());
		assertEquals("NAME", countryDto2.getName());
		assertEquals("DESCRIPTION", countryDto2.getDescription());
	}

	@Test
	public void testEDeleteCountryById() {
		this.logger.info("===> testDeleteCountryById()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_DELETE_BY_ID + "6";
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.DELETE, null, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody());
		// Double check action
		ResponseEntity<CountryDto> response2 = findById(6L);
		CountryDto countryDto2 = response2.getBody();
		assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
		assertNull(countryDto2);
	}

	@Test
	public void testFFindCountryById() {
		this.logger.info("===> testFindCountryById()");
		ResponseEntity<CountryDto> response = findById(2L);
		CountryDto countryDto = response.getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("France", countryDto.getName());
		assertEquals("Cardif's headquarter", countryDto.getDescription());
	}

	@Test
	public void testGFindInexistentCountryById() {
		this.logger.info("===> testFindInexistentCountryById()");
		ResponseEntity<CountryDto> response = findById(10L);
		CountryDto countryDto = response.getBody();
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(countryDto);
	}

	@Test
	public void testHModifyInexistentCountryById() {
		this.logger.info("===> testModifyInexistentCountryById()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_MODIFY_BY_ID + "10";
		ResponseEntity<Boolean> response = null;
		CountryDto countryDto = new CountryDto();
		countryDto.setName("NAME");
		countryDto.setDescription("DESCRIPTION");
		HttpEntity<CountryDto> httpEntity = new HttpEntity<>(countryDto);
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT, httpEntity, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertFalse(response.getBody());
	}

	@Test
	public void testIDeleteInexistentCountryById() {
		this.logger.info("===> testDeleteInexistentCountryById()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_DELETE_BY_ID + "10";
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.DELETE, null, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertFalse(response.getBody());
	}

	@Test
	public void testJCreateCountryWithException() {
		this.logger.info("===> testCreateCountryWithException()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_CREATE;
		ResponseEntity<Boolean> response = null;
		Boolean value = null;
		try {
			response = restTemplate.postForEntity(new URL(url).toString(), null, Boolean.class);
			value = response.getBody();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertFalse(value);
	}

	@Test
	public void testKModifyCountryByIdWithSpaces1() {
		this.logger.info("===> testModifyCountryByIdWithSpaces1()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_MODIFY_BY_ID + "3";
		CountryDto countryDto = new CountryDto();
		countryDto.setName("  NAME3  ");
		countryDto.setDescription("    ");
		HttpEntity<CountryDto> httpEntity = new HttpEntity<>(countryDto);
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT, httpEntity, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody());
		// Double check action
		ResponseEntity<CountryDto> response2 = findById(3L);
		CountryDto countryDto2 = response2.getBody();
		assertEquals(HttpStatus.OK, response2.getStatusCode());
		assertEquals("NAME3", countryDto2.getName());
		assertEquals("There is too much sun", countryDto2.getDescription());
	}

	@Test
	public void testLModifyCountryByIdWithSpaces2() {
		this.logger.info("===> testModifyCountryByIdWithSpaces2()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_MODIFY_BY_ID + "5";
		CountryDto countryDto = new CountryDto();
		countryDto.setName("    ");
		countryDto.setDescription("  DESCRIPTION5  ");
		HttpEntity<CountryDto> httpEntity = new HttpEntity<>(countryDto);
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT, httpEntity, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody());
		// Double check action
		ResponseEntity<CountryDto> response2 = findById(5L);
		CountryDto countryDto2 = response2.getBody();
		assertEquals(HttpStatus.OK, response2.getStatusCode());
		assertEquals("Canada", countryDto2.getName());
		assertEquals("DESCRIPTION5", countryDto2.getDescription());
	}

	@Test
	public void testMModifyCountryByIdWithNulls() {
		this.logger.info("===> testModifyCountryByIdWithNulls()");
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_MODIFY_BY_ID + "4";
		CountryDto countryDto = new CountryDto();
		HttpEntity<CountryDto> httpEntity = new HttpEntity<>(countryDto);
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT, httpEntity, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody());
		// Double check action
		ResponseEntity<CountryDto> response2 = findById(4L);
		CountryDto countryDto2 = response2.getBody();
		assertEquals(HttpStatus.OK, response2.getStatusCode());
		assertEquals("Italy", countryDto2.getName());
		assertEquals("There is mafia overthere", countryDto2.getDescription());
	}

	private ResponseEntity<CountryDto> findById(Long id) {
		String url = getUrlBase() + URI_COUNTRY_ENDPOINT_FIND_BY_ID + id;
		try {
			return restTemplate.getForEntity(new URL(url).toString(), CountryDto.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}