package org.example.bestioles.repository;

import org.example.bestioles.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = false)
public interface PersonRepository extends CrudRepository<Person, Integer>, PersonRepositoryCustom {

    Iterable<Person> findAllByLastNameOrFirstName(String lastName, String firstName);

    Iterable<Person> findAllByAgeGreaterThanEqual(Integer age);

    @Query("SELECT p FROM Person p WHERE p.age BETWEEN :minAge AND :maxAge")
    Iterable<Person> findAllByAgeBetween(Integer minAge, Integer maxAge);

    @Query("SELECT p FROM Person p JOIN p.animals a WHERE a.name = :animalName")
    Iterable<Person> findAllByAnimalName(String animalName);

    @Modifying
    void deleteByLastName(String lastName);

    Page<Person> findAll(Pageable pageable);
}
