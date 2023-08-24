package org.springboot.app.service.impl;

import static org.springboot.app.constants.Constants.MSG_CANT_DELETE_COUNTRY;
import static org.springboot.app.constants.Constants.MSG_CANT_RETURN_COUNTRIES;
import static org.springboot.app.constants.Constants.MSG_CANT_RETURN_COUNTRY;
import static org.springboot.app.constants.Constants.MSG_CANT_SAVE_COUNTRY;
import static org.springboot.app.constants.Constants.MSG_PRE_ERROR;

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
import org.springboot.app.service.CountryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author jmedina
 *
 */
@Service
@Validated
public class CountryServiceImpl implements CountryService {

	private final Logger logger = LogManager.getLogger(CountryServiceImpl.class);

	private final CountryRepository countryRepository;

	public CountryServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	public List<CountryDto> findAll() throws ServiceException {
		this.logger.debug("===> processing findAll()");
		final List<CountryDto> listado = new ArrayList<>();
		try {
			StreamSupport.stream(this.countryRepository.findAll().spliterator(), false).map(CountryDto::new)
					.forEach(listado::add);
		} catch (Exception e) {
			this.logger.error(MSG_PRE_ERROR, e.getMessage());
			throw new ServiceException(MSG_CANT_RETURN_COUNTRIES, e);
		}
		return listado;
	}

	public Optional<CountryDto> findById(@NotNull Long id) throws ServiceException {
		this.logger.debug("===> processing findById()");
		final Optional<Country> country = getById(id);
		if (country.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new CountryDto(country.get()));
	}

	public boolean create(@NotNull @Valid Country country) throws ServiceException {
		this.logger.debug("===> processing create()");
		try {
			this.countryRepository.save(country);
		} catch (Exception e) {
			this.logger.error(MSG_PRE_ERROR, e.getMessage());
			throw new ServiceException(MSG_CANT_SAVE_COUNTRY, e);
		}
		return true;
	}

	public boolean update(@NotNull Country countryReq, @NotNull Long id) throws ServiceException {
		this.logger.debug("===> processing update()");
		final Optional<Country> countryFromDB = getById(id);
		if (countryFromDB.isEmpty()) {
			return false;
		}
		final Country country = countryFromDB.get();
		final String name = countryReq.getName();
		final String desc = countryReq.getDescription();
		if (Objects.nonNull(name) && !name.trim().isEmpty()) {
			country.setName(name.trim());
		}
		if (Objects.nonNull(desc) && !desc.trim().isEmpty()) {
			country.setDescription(desc.trim());
		}
		try {
			this.countryRepository.save(country);
		} catch (Exception e) {
			this.logger.error(MSG_PRE_ERROR, e.getMessage());
			throw new ServiceException(MSG_CANT_SAVE_COUNTRY, e);
		}
		return true;
	}

	public boolean delete(@NotNull Long id) throws ServiceException {
		this.logger.debug("===> processing delete()");
		final Optional<Country> countryFromDB = getById(id);
		if (countryFromDB.isEmpty()) {
			return false;
		}
		try {
			this.countryRepository.deleteById(id);
		} catch (Exception e) {
			this.logger.error(MSG_PRE_ERROR, e.getMessage());
			throw new ServiceException(MSG_CANT_DELETE_COUNTRY, e);
		}
		return true;
	}

	private Optional<Country> getById(@NotNull Long id) throws ServiceException {
		try {
			return this.countryRepository.findById(id);
		} catch (Exception e) {
			this.logger.error(MSG_PRE_ERROR, e.getMessage());
			throw new ServiceException(MSG_CANT_RETURN_COUNTRY, e);
		}
	}
}
