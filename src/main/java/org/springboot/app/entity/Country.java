package org.springboot.app.entity;

import static org.springboot.app.constants.Constants.MSG_COUNTRY_DESC_IS_BLANK;
import static org.springboot.app.constants.Constants.MSG_COUNTRY_DESC_IS_EMPTY;
import static org.springboot.app.constants.Constants.MSG_COUNTRY_DESC_IS_NULL;
import static org.springboot.app.constants.Constants.MSG_COUNTRY_NAME_IS_BLANK;
import static org.springboot.app.constants.Constants.MSG_COUNTRY_NAME_IS_EMPTY;
import static org.springboot.app.constants.Constants.MSG_COUNTRY_NAME_IS_NULL;
import static org.springboot.app.constants.Constants.TABLE_COUNTRY;
import static org.springboot.app.constants.Constants.TABLE_COUNTRY_COLUMN_DESCRIPTION;
import static org.springboot.app.constants.Constants.TABLE_COUNTRY_COLUMN_NAME;

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
@Table(name = TABLE_COUNTRY)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = TABLE_COUNTRY_COLUMN_NAME)
	@NotNull(message = MSG_COUNTRY_NAME_IS_NULL)
	@NotEmpty(message = MSG_COUNTRY_NAME_IS_EMPTY)
	@NotBlank(message = MSG_COUNTRY_NAME_IS_BLANK)
	private String name;

	@Column(name = TABLE_COUNTRY_COLUMN_DESCRIPTION)
	@NotNull(message = MSG_COUNTRY_DESC_IS_NULL)
	@NotEmpty(message = MSG_COUNTRY_DESC_IS_EMPTY)
	@NotBlank(message = MSG_COUNTRY_DESC_IS_BLANK)
	private String description;

	public Country(CountryDto dto) {
		this.name = dto.getName().trim();
		this.description = dto.getDescription().trim();
	}

	@Override
	public boolean equals(Object obj) {
		if (Objects.isNull(obj) || !(obj instanceof Country)) {
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