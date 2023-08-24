package org.springboot.app.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import static org.springboot.app.constants.Constants.MSG_CANT_RETURN_COUNTRIES;
import static org.springboot.app.constants.Constants.MSG_CANT_RETURN_COUNTRY;
import static org.springboot.app.constants.Constants.MSG_CANT_SAVE_COUNTRY;
import static org.springboot.app.constants.Constants.MSG_CANT_UPDATE_COUNTRY;
import static org.springboot.app.constants.Constants.MSG_CANT_DELETE_COUNTRY;

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
import org.springboot.app.entity.Country;
import org.springboot.app.exception.ServiceException;
import org.springboot.app.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jmedina
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CountryControllerWithMockTest extends AppTestHelper {

	private final Logger logger = LogManager.getLogger(CountryControllerWithMockTest.class);

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private CountryService countryService;

	public CountryControllerWithMockTest() {
	}

	@Test
	public void testFindAllCountriesWithException() {
		this.logger.info("===> testFindAllCountriesWithException()");
		try {
			when(countryService.findAll()).thenThrow(new ServiceException(MSG_CANT_RETURN_COUNTRIES));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		String url = getUrlBase() + "/find";
		ResponseEntity<CountryDto[]> response = null;
		CountryDto[] objects = null;
		try {
			response = restTemplate.getForEntity(new URL(url).toString(), CountryDto[].class);
			objects = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNull(objects);
	}

	@Test
	public void testFindCountriesWithExceptionById() {
		this.logger.info("===> testFindCountriesWithExceptionById()");
		try {
			when(countryService.findById(1L)).thenThrow(new ServiceException(MSG_CANT_RETURN_COUNTRY));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		String url = getUrlBase() + "/findById/1";
		ResponseEntity<CountryDto[]> response = null;
		CountryDto[] objects = null;
		try {
			response = restTemplate.getForEntity(new URL(url).toString(), CountryDto[].class);
			objects = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNull(objects);
	}

	@Test
	public void testCreateCountryWithException() {
		this.logger.info("===> testCreateCountryWithException()");
		CountryDto countryDto = new CountryDto();
		countryDto.setName("NAME");
		countryDto.setDescription("DESCRIPTION");
		Country country = new Country(countryDto);
		try {
			when(countryService.create(country)).thenThrow(new ServiceException(MSG_CANT_SAVE_COUNTRY));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		String url = getUrlBase() + "/create";
		ResponseEntity<Object> response = null;
		Object object = null;
		try {
			response = restTemplate.postForEntity(new URL(url).toString(), countryDto, Object.class);
			object = response.getBody();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(false, object);
	}

	@Test
	public void testModifyCountryWithException() {
		this.logger.info("===> testModifyCountryWithException()");
		CountryDto countryDto = new CountryDto();
		countryDto.setDescription("DESCRIPTION");
		Country country = new Country(countryDto);
		try {
			when(countryService.update(country, 1L)).thenThrow(new ServiceException(MSG_CANT_UPDATE_COUNTRY));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		String url = getUrlBase() + "/modify/1";
		HttpEntity<CountryDto> httpEntity = new HttpEntity<>(countryDto);
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT, httpEntity, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(false, response.getBody());
	}

	@Test
	public void testDeleteCountryWithException() {
		this.logger.info("===> testDeleteCountryWithException()");
		try {
			when(countryService.delete(1L)).thenThrow(new ServiceException(MSG_CANT_DELETE_COUNTRY));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		String url = getUrlBase() + "/delete/1";
		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.exchange(new URL(url).toString(), HttpMethod.DELETE, null, Boolean.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(false, response.getBody());
	}

}
