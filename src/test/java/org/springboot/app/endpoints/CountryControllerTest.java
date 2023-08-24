package org.springboot.app.endpoints;

import static org.junit.Assert.assertEquals;
import static org.springboot.app.constants.Constants.MSG_HELLO;

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
	public void testHello() {
		this.logger.info("===> testHello()");
		String url = getUrlBase() + "/";
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
	public void testFindCountries() {
		this.logger.info("===> testFindCountries()");
		String url = getUrlBase() + "/find";
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
	}

	@Test
	public void testCreateCountry() {
		this.logger.info("===> testCreateCountry()");
		String url = getUrlBase() + "/create";
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
		assertEquals(true, value);
	}

	@Test
	public void testModifyCountryById() {
		this.logger.info("===> testModifyCountryById()");
		String url = getUrlBase() + "/modify/1";
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
		assertEquals(true, response.getBody());
	}

	@Test
	public void testDeleteCountryById() {
		this.logger.info("===> testDeleteCountryById()");
		String url = getUrlBase() + "/delete/6";
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.DELETE, null, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody());
	}

	@Test
	public void testFindCountryById() {
		this.logger.info("===> testFindCountryById()");
		String url = getUrlBase() + "/findById/1";
		ResponseEntity<CountryDto> response = null;
		CountryDto countryDto = null;
		try {
			response = restTemplate.getForEntity(new URL(url).toString(), CountryDto.class);
			countryDto = response.getBody();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("USA", countryDto.getName());
		assertEquals("Neighbor at top", countryDto.getDescription());
	}

	@Test
	public void testFindInexistentCountryById() {
		this.logger.info("===> testFindInexistentCountryById()");
		String url = getUrlBase() + "/findById/10";
		ResponseEntity<CountryDto> response = null;
		CountryDto countryDto = null;
		try {
			response = restTemplate.getForEntity(new URL(url).toString(), CountryDto.class);
			countryDto = response.getBody();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(null, countryDto);
	}

	@Test
	public void testModifyInexistentCountryById() {
		this.logger.info("===> testModifyInexistentCountryById()");
		String url = getUrlBase() + "/modify/10";
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
		assertEquals(false, response.getBody());
	}

	@Test
	public void testDeleteInexistentCountryById() {
		this.logger.info("===> testDeleteInexistentCountryById()");
		String url = getUrlBase() + "/delete/10";
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.DELETE, null, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(false, response.getBody());
	}

	@Test
	public void testCreateCountryWithException() {
		this.logger.info("===> testCreateCountryWithException()");
		String url = getUrlBase() + "/create";
		ResponseEntity<Boolean> response = null;
		Boolean value = null;
		try {
			response = restTemplate.postForEntity(new URL(url).toString(), null, Boolean.class);
			value = response.getBody();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(false, value);
	}

	@Test
	public void testModifyCountryByIdWithSpaces() {
		this.logger.info("===> testModifyCountryByIdWithSpaces()");
		String url = getUrlBase() + "/modify/1";
		CountryDto countryDto = new CountryDto();
		countryDto.setName("    ");
		countryDto.setDescription("    ");
		HttpEntity<CountryDto> httpEntity = new HttpEntity<>(countryDto);
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT, httpEntity, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody());
	}

	@Test
	public void testModifyCountryByIdWithNulls() {
		this.logger.info("===> testModifyCountryByIdWithNulls()");
		String url = getUrlBase() + "/modify/1";
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
	}
}