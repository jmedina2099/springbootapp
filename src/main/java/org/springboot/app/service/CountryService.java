package org.springboot.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springboot.app.dto.CountryDto;
import org.springboot.app.entity.Country;
import org.springboot.app.exception.ServiceException;
import org.springboot.app.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author jmedina
 *
 */
@Service
@Validated
public class CountryService {

	private final Logger logger = LogManager.getLogger(CountryService.class);

	private final CountryRepository countryRepository;

	private static final String PRE_ERROR_MSG = "**=> error: {}";

	public CountryService(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	public List<CountryDto> findAll() throws ServiceException {
		this.logger.info("===> processing findAll()");
		final List<CountryDto> listado = new ArrayList<>();
		try {
			StreamSupport.stream(this.countryRepository.findAll().spliterator(), true).map(CountryDto::new)
					.forEach(listado::add);
		} catch (Exception e) {
			this.logger.error(PRE_ERROR_MSG, e.getMessage());
			throw new ServiceException("No fue posible regresar el listado de paises.", e);
		}
		return listado;
	}

	public Optional<CountryDto> findById(Long id) throws ServiceException {
		this.logger.info("===> processing findById()");
		final Optional<Country> country = getById(id);
		if (country.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new CountryDto(country.get()));
	}

	public boolean create(@Valid Country country) throws ServiceException {
		this.logger.info("===> processing create()");
		try {
			this.countryRepository.save(country);
		} catch (Exception e) {
			this.logger.error(PRE_ERROR_MSG, e.getMessage());
			throw new ServiceException("No fue posible guardar el pais.", e);
		}
		return true;
	}

	public boolean update(Country countryReq, @NotNull Long id) throws ServiceException {
		this.logger.info("===> processing update()");
		final Optional<Country> countryFromDB = getById(id);
		if (countryFromDB.isEmpty()) {
			return false;
		}
		final Country country = countryFromDB.get();
		final String name = countryReq.getName();
		final String desc = countryReq.getDescription();
		if (Objects.nonNull(name) && !name.trim().isEmpty()) {
			country.setName(name);
		}
		if (Objects.nonNull(desc) && !desc.trim().isEmpty()) {
			country.setDescription(desc);
		}
		this.countryRepository.save(country);
		return true;
	}

	public boolean delete(@NotNull Long id) throws ServiceException {
		this.logger.info("===> processing delete()");
		final Optional<Country> countryFromDB = getById(id);
		if (countryFromDB.isEmpty()) {
			return false;
		}
		this.countryRepository.deleteById(id);
		return true;
	}

	private Optional<Country> getById(@NotNull Long id) throws ServiceException {
		try {
			return this.countryRepository.findById(id);
		} catch (Exception e) {
			this.logger.error(PRE_ERROR_MSG, e.getMessage());
			throw new ServiceException("No fue posible regresar el pais.", e);
		}
	}
}
