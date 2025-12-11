package org.example.bestioles.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.bestioles.model.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Repository
public class PersonRepositoryCustomImpl implements PersonRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deletePersonsWithoutAnimals() {
        var persons = entityManager.createQuery("SELECT p FROM Person p WHERE p.animals IS EMPTY", Person.class).getResultList();
        for (Person p : persons) {
            entityManager.remove(p);
        }
    }

    @Override
    @Transactional
    public void generatePersons(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            Person person = new Person();
            person.setFirstName("First_" + random.nextInt(10000));
            person.setLastName("Last_" + random.nextInt(10000));
            person.setLogin("login_" + random.nextInt(10000));
            person.setMdp("mdp_" + random.nextInt(10000));
            person.setAge(random.nextInt(100));
            person.setActive((byte)1);
            entityManager.persist(person);
        }
    }
}
