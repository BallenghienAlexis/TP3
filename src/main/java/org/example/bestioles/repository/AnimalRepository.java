package org.example.bestioles.repository;

import org.example.bestioles.model.Animal;
import org.example.bestioles.model.Species;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Integer> {

    Iterable<Animal> findAllBySpecies(Species species);

    Iterable<Animal> findAllByColorIn(Collection<String> color);
}
