package org.springboot.app.dto;

import java.util.Objects;

import org.springboot.app.entity.Country;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author jmedina
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryDto {

	private String name;
	private String description;

	public CountryDto(Country country) {
		this.name = country.getName();
		this.description = country.getDescription();
	}

	@Override
	public boolean equals(Object obj) {
		if (Objects.isNull(obj)) {
			return false;
		}
		final CountryDto countryDto = (CountryDto) obj;
		return Objects.equals(this.name, countryDto.getName())
				&& Objects.equals(this.description, countryDto.getDescription());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.description);
	}

}