package org.springboot.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class CountryController {

	private final Logger logger = LogManager.getLogger(CountryController.class);

	private final CountryService countryService;

	private static final String PRE_ERROR_MSG = "**=> error: {}";

	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping("/")
	public ResponseEntity<String> hello() {
		this.logger.info("==> processing hello()");
		return new ResponseEntity<>("OK!", HttpStatus.OK);
	}

	@GetMapping("/find")
	public ResponseEntity<List<CountryDto>> find() {
		this.logger.info("==> processing find()");
		List<CountryDto> listado = new ArrayList<>();
		try {
			listado = this.countryService.findAll();
		} catch (Exception e) {
			this.logger.error(PRE_ERROR_MSG, e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(listado, HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<CountryDto> findById(@NotNull @PathVariable Long id) {
		this.logger.info("==> processing findById()");
		Optional<CountryDto> country = Optional.empty();
		try {
			country = this.countryService.findById(id);
		} catch (Exception e) {
			this.logger.error(PRE_ERROR_MSG, e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (country.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(country.get(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Boolean> create(@NotNull @RequestBody CountryDto countryDto) {
		this.logger.info("==> processing create()");
		try {
			this.countryService.create(new Country(countryDto));
		} catch (Exception e) {
			this.logger.error(PRE_ERROR_MSG, e.getMessage());
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@PutMapping("/modify/{id}")
	public ResponseEntity<Boolean> modify(@NotNull @RequestBody CountryDto countryDto, @NotNull @PathVariable Long id) {
		this.logger.info("==> processing modify()");
		try {
			if (!this.countryService.update(new Country(countryDto), id)) {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			this.logger.error(PRE_ERROR_MSG, e.getMessage());
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> delete(@NotNull @PathVariable Long id) {
		this.logger.info("==> processing delete()");
		try {
			if (!this.countryService.delete(id)) {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			this.logger.error(PRE_ERROR_MSG, e.getMessage());
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Boolean> handleException(Exception e) {
		this.logger.info("==> processing handleException()");
		this.logger.error(PRE_ERROR_MSG, e.getMessage());
		return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
