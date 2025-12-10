package org.example.bestioles;

import org.example.bestioles.model.Animal;
import org.example.bestioles.model.Person;
import org.example.bestioles.model.Role;
import org.example.bestioles.model.Species;
import org.example.bestioles.repository.SpeciesRepository;
import org.example.bestioles.repository.AnimalRepository;
import org.example.bestioles.repository.PersonRepository;
import org.example.bestioles.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BestiolesApplication implements CommandLineRunner {

    AnimalRepository animalRepository;
    PersonRepository personRepository;
    RoleRepository roleRepository;
    SpeciesRepository speciesRepository;

    @Autowired
    public BestiolesApplication(AnimalRepository animalRepository,
                               PersonRepository personRepository,
                               RoleRepository roleRepository,
                               SpeciesRepository speciesRepository) {
        this.animalRepository = animalRepository;
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.speciesRepository = speciesRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BestiolesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Application started
        List<Animal> animals = (List<Animal>) animalRepository.findAll();
        List<Person> persons = (List<Person>) personRepository.findAll();
        List<Species> species = (List<Species>) speciesRepository.findAll();
        List<Role> roles = (List<Role>) roleRepository.findAll();

        System.out.println("\nAnimals: ");
        animals.forEach( it ->System.out.println(it.getFullDescription()) );

        System.out.println("\nPersons: ");
        persons.forEach( it ->System.out.println(it.getFullName()) );

        System.out.println("\nSpecies: ");
        species.forEach( it ->System.out.println(it.toString()) );

        System.out.println("\nRoles: ");
        roles.forEach( it ->System.out.println(it.getLabel()) );

        Animal animal = new Animal();
        animal.setName("Leo");
        animal.setSex("M");
        animal.setColor("Brun");
        animal.setSpecies(speciesRepository.findByCommonName("Chien").orElse(null));
        animalRepository.save(animal);

        animal = animalRepository.findById(animal.getId()).orElse(null);
        assert animal != null;
        System.out.println("\nNew Animal added: " + animal.getFullDescription());

        animalRepository.delete(animal);

        animal = animalRepository.findById(animal.getId()).orElse(null);
        System.out.println("Animal deleted, found = " + (animal != null));

    }
}
