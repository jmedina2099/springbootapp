package org.springboot.app.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springboot.app.dto.CountryDto;
import org.springboot.app.entity.Country;
import org.springboot.app.exception.ServiceException;

/**
 * @author jmedina
 *
 */
public interface CountryService {

	public List<CountryDto> findAll() throws ServiceException;

	public Optional<CountryDto> findById(@NotNull Long id) throws ServiceException;

	public boolean create(@NotNull @Valid Country country) throws ServiceException;

	public boolean update(@NotNull Country countryReq, @NotNull Long id) throws ServiceException;

	public boolean delete(@NotNull Long id) throws ServiceException;

}