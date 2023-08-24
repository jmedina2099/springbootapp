package org.springboot.app.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springboot.app.dto.CountryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author jmedina
 *
 */
public interface CountryController {

	public ResponseEntity<String> hello();

	public ResponseEntity<List<CountryDto>> find();

	public ResponseEntity<CountryDto> findById(@NotNull @PathVariable Long id);

	public ResponseEntity<Boolean> create(@NotNull @RequestBody CountryDto countryDto);

	public ResponseEntity<Boolean> modify(@NotNull @RequestBody CountryDto countryDto, @NotNull @PathVariable Long id);

	public ResponseEntity<Boolean> delete(@NotNull @PathVariable Long id);

}
