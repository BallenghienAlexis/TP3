package org.example.bestioles.service;

import org.example.bestioles.model.Species;
import org.example.bestioles.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpeciesService {
    private final SpeciesRepository speciesRepository;

    @Autowired
    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public Species create(Species species) {
        return speciesRepository.save(species);
    }

    public Species update(Species species) {
        return speciesRepository.save(species);
    }

    public void delete(Integer id) {
        speciesRepository.deleteById(id);
    }

    public List<Species> findAll() {
        return (List<Species>) speciesRepository.findAll();
    }

    public Optional<Species> findById(Integer id) {
        return speciesRepository.findById(id);
    }
}
