package org.springboot.app.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springboot.app.dto.CountryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author jmedina
 *
 */
@Entity
@Table(name = "COUNTRIES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	@NotNull(message = "Name is null")
	@NotEmpty(message = "Name is empty")
	@NotBlank(message = "Name is blank")
	private String name;

	@Column(name = "DESCRIPTION")
	@NotNull(message = "Description is null")
	@NotEmpty(message = "Description is empty")
	@NotBlank(message = "Description is blank")
	private String description;

	public Country(CountryDto dto) {
		this.name = dto.getName();
		this.description = dto.getDescription();
	}

	@Override
	public boolean equals(Object obj) {
		if (Objects.isNull(obj)) {
			return false;
		}
		final Country country = (Country) obj;
		return Objects.equals(this.id, country.getId()) && Objects.equals(this.name, country.getName())
				&& Objects.equals(this.description, country.getDescription());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.description);
	}
}