package org.example.bestioles.repository;

import org.example.bestioles.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    Iterable<Person> findAllByLastNameOrFirstName(String lastName, String firstName);

    Iterable<Person> findAllByAgeGreaterThanEqual(Integer age);
}
