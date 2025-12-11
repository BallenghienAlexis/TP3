package org.example.bestioles.service;

import org.example.bestioles.model.Person;
import org.example.bestioles.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public Person update(Person person) {
        return personRepository.save(person);
    }

    public void delete(Long id) {
        personRepository.deleteById(Math.toIntExact(id));
    }

    public List<Person> findAll() {
        return (List<Person>) personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(Math.toIntExact(id));
    }

    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }
}

