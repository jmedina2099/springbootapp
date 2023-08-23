package org.springboot.app.repository;

import org.springboot.app.entity.Country;
import org.springframework.data.repository.CrudRepository;

/**
 * @author jmedina
 *
 */
public interface CountryRepository extends CrudRepository<Country, Long> {
}