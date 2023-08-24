package org.springboot.app.controller.impl;

import static org.springboot.app.constants.Constants.MSG_HELLO;
import static org.springboot.app.constants.Constants.MSG_PRE_ERROR;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_CREATE;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_DELETE_BY_ID;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_FIND;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_FIND_BY_ID;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_HELLO;
import static org.springboot.app.constants.Constants.URI_COUNTRY_ENDPOINT_MODIFY_BY_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springboot.app.controller.CountryController;
import org.springboot.app.dto.CountryDto;
import org.springboot.app.entity.Country;
import org.springboot.app.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jmedina
 *
 */
@RestController
public class CountryControllerImpl implements CountryController {

	private final Logger logger = LogManager.getLogger(CountryControllerImpl.class);

	private final CountryService countryService;

	public CountryControllerImpl(CountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping(URI_COUNTRY_ENDPOINT_HELLO)
	public ResponseEntity<String> hello() {
		this.logger.debug("==> processing hello()");
		return new ResponseEntity<>(MSG_HELLO, HttpStatus.OK);
	}

	@GetMapping(URI_COUNTRY_ENDPOINT_FIND)
	public ResponseEntity<List<CountryDto>> find() {
		this.logger.debug("==> processing find()");
		List<CountryDto> listado = new ArrayList<>();
		try {
			listado = this.countryService.findAll();
		} catch (Exception e) {
			this.logger.error(MSG_PRE_ERROR, e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(listado, HttpStatus.OK);
	}

	@GetMapping(URI_COUNTRY_ENDPOINT_FIND_BY_ID + "{id}")
	public ResponseEntity<CountryDto> findById(@NotNull @PathVariable Long id) {
		this.logger.debug("==> processing findById()");
		Optional<CountryDto> country = Optional.empty();
		try {
			country = this.countryService.findById(id);
		} catch (Exception e) {
			this.logger.error(MSG_PRE_ERROR, e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (country.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(country.get(), HttpStatus.OK);
	}

	@PostMapping(URI_COUNTRY_ENDPOINT_CREATE)
	public ResponseEntity<Boolean> create(@NotNull @RequestBody CountryDto countryDto) {
		this.logger.debug("==> processing create()");
		try {
			this.countryService.create(new Country(countryDto));
		} catch (Exception e) {
			this.logger.error(MSG_PRE_ERROR, e.getMessage());
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@PutMapping(URI_COUNTRY_ENDPOINT_MODIFY_BY_ID + "{id}")
	public ResponseEntity<Boolean> modify(@NotNull @RequestBody CountryDto countryDto, @NotNull @PathVariable Long id) {
		this.logger.debug("==> processing modify()");
		try {
			if (!this.countryService.update(new Country(countryDto), id)) {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			this.logger.error(MSG_PRE_ERROR, e.getMessage());
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@DeleteMapping(URI_COUNTRY_ENDPOINT_DELETE_BY_ID + "{id}")
	public ResponseEntity<Boolean> delete(@NotNull @PathVariable Long id) {
		this.logger.debug("==> processing delete()");
		try {
			if (!this.countryService.delete(id)) {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			this.logger.error(MSG_PRE_ERROR, e.getMessage());
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Boolean> handleException(Exception e) {
		this.logger.debug("==> processing handleException()");
		this.logger.error(MSG_PRE_ERROR, e.getMessage());
		return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}