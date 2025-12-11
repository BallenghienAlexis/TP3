package org.example.bestioles.repository;

import org.example.bestioles.model.Animal;
import org.example.bestioles.model.Species;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Integer> {

    Iterable<Animal> findAllBySpecies(Species species);

    Iterable<Animal> findAllByColorIn(Collection<String> color);

    @Query("SELECT COUNT(a) FROM Animal a WHERE a.sex = :sex")
    Long countBySex(String sex);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Animal a JOIN a.persons p WHERE a = :animal")
    boolean existsByAnimalBelongsToAnyPerson(Animal animal);
}
