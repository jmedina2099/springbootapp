package org.springboot.app.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springboot.app.constants.Constants.MSG_CANT_RETURN_COUNTRIES;
import static org.springboot.app.constants.Constants.MSG_CANT_SAVE_COUNTRY;
import static org.springboot.app.constants.Constants.MSG_CANT_RETURN_COUNTRY;
import static org.springboot.app.constants.Constants.MSG_CANT_UPDATE_COUNTRY;
import static org.springboot.app.constants.Constants.MSG_CANT_DELETE_COUNTRY;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springboot.app.AppTestHelper;
import org.springboot.app.dto.CountryDto;
import org.springboot.app.entity.Country;
import org.springboot.app.repository.CountryRepository;
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
public class CountryServiceWithMockTest extends AppTestHelper {

	private final Logger logger = LogManager.getLogger(CountryServiceWithMockTest.class);

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private CountryRepository countryRepository;

	public CountryServiceWithMockTest() {
	}

	@Test
	public void testFindAllCountriesWithException() {
		this.logger.info("===> testFindAllCountriesWithException()");
		when(countryRepository.findAll()).thenThrow(new RuntimeException(MSG_CANT_RETURN_COUNTRIES));
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
	public void testCreateCountryWithException() {
		this.logger.info("===> testCreateCountryWithException()");
		CountryDto countryDto = new CountryDto();
		countryDto.setName("NAME");
		countryDto.setDescription("DESCRIPTION");
		Country country = new Country(countryDto);
		when(countryRepository.save(country)).thenThrow(new RuntimeException(MSG_CANT_SAVE_COUNTRY));
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
	public void testFindCountriesWithExceptionById() {
		this.logger.info("===> testFindCountriesWithExceptionById()");
		when(countryRepository.findById(1L)).thenThrow(new RuntimeException(MSG_CANT_RETURN_COUNTRY));
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
	public void testUpdateCountryWithExceptionById() {
		this.logger.info("===> testUpdateCountryWithExceptionById()");
		CountryDto countryDto = new CountryDto();
		countryDto.setName("NAME");
		countryDto.setDescription("DESCRIPTION");
		Country country = new Country(countryDto);
		country.setId(1L);
		when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
		when(countryRepository.save(country)).thenThrow(new RuntimeException(MSG_CANT_UPDATE_COUNTRY));
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
	public void testDeleteCountryWithExceptionById() {
		this.logger.info("===> testDeleteCountryWithExceptionById()");
		CountryDto countryDto = new CountryDto();
		countryDto.setName("NAME");
		countryDto.setDescription("DESCRIPTION");
		Country country = new Country(countryDto);
		country.setId(1L);
		when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
		doThrow(new RuntimeException(MSG_CANT_DELETE_COUNTRY)).when(countryRepository).deleteById(1L);
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
