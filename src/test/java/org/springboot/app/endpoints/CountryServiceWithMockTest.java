package org.springboot.app.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

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
import org.springboot.app.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
		when(countryRepository.findAll())
				.thenThrow(new RuntimeException("No fue posible regresar el listado de paises."));
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
		when(countryRepository.save(country)).thenThrow(new RuntimeException("No fue posible guardar el pais."));
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
		when(countryRepository.findById(1L))
				.thenThrow(new RuntimeException("No fue posible regresar el pais."));
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
	
}
