package org.example.bestioles.repository;

import org.example.bestioles.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    Iterable<Person> findAllByLastNameOrFirstName(String lastName, String firstName);

    Iterable<Person> findAllByAgeGreaterThanEqual(Integer age);

    @Query("SELECT p FROM Person p WHERE p.age BETWEEN :minAge AND :maxAge")
    Iterable<Person> findAllByAgeBetween(Integer minAge, Integer maxAge);

    @Query("SELECT p FROM Person p JOIN p.animals a WHERE a.name = :animalName")
    Iterable<Person> findAllByAnimalName(String animalName);
}
