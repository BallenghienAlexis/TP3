package org.example.bestioles.repository;

import org.example.bestioles.model.Species;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpeciesRepository extends CrudRepository<Species, Integer> {

    Optional<Species> findByCommonName(String name);

    Optional<Species> findFirstByCommonNameContaining (String commonNamePart);

    Iterable<Species> findAllByLatinNameContainingIgnoreCase (String latinNamePart);
}
