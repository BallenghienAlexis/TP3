package org.example.bestioles.service;

import org.example.bestioles.model.Animal;
import org.example.bestioles.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    public Animal create(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal update(Animal animal) {
        return animalRepository.save(animal);
    }

    public void delete(Long id) {
        animalRepository.deleteById(Math.toIntExact(id));
    }

    public List<Animal> findAll() {
        return (List<Animal>) animalRepository.findAll();
    }

    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(Math.toIntExact(id));
    }
}
