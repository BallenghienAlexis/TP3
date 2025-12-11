package org.example.bestioles;

import org.example.bestioles.model.Animal;
import org.example.bestioles.model.Person;
import org.example.bestioles.model.Role;
import org.example.bestioles.model.Species;
import org.example.bestioles.repository.AnimalRepository;
import org.example.bestioles.repository.PersonRepository;
import org.example.bestioles.repository.RoleRepository;
import org.example.bestioles.repository.SpeciesRepository;
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
    public BestiolesApplication(AnimalRepository animalRepository, PersonRepository personRepository, RoleRepository roleRepository, SpeciesRepository speciesRepository) {
        this.animalRepository = animalRepository;
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.speciesRepository = speciesRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BestiolesApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("----- BESTIOLES APPLICATION -----");
        System.out.println("TP3");
        System.out.println("Listing all entities in the database:");
        List<Animal> animals = (List<Animal>) animalRepository.findAll();
        List<Person> persons = (List<Person>) personRepository.findAll();
        List<Species> species = (List<Species>) speciesRepository.findAll();
        List<Role> roles = (List<Role>) roleRepository.findAll();

        System.out.println("\nAnimaux : ");
        animals.forEach(System.out::println);

        System.out.println("\nPersonnes : ");
        persons.forEach(System.out::println);

        System.out.println("\nEspèces : ");
        species.forEach(System.out::println);

        System.out.println("\nRôles : ");
        roles.forEach(System.out::println);

        // --- Ajout d'un nouvel animal ---
        System.out.println("\nAjout d'un nouvel animal :");
        Animal animal = new Animal();
        animal.setName("Leo");
        animal.setSex("M");
        animal.setColor("Brun");
        animal.setSpecies(speciesRepository.findByCommonName("Chien").orElse(null));
        animalRepository.save(animal);

        animal = animalRepository.findById(animal.getId()).orElse(null);
        assert animal != null;
        System.out.println("\nNouvel animal ajouté : " + animal);

        // --- Suppression de l'animal ajouté ---
        System.out.println("\nSuppression du nouvel animal ajouté :");
        animalRepository.delete(animal);
        animal = animalRepository.findById(animal.getId()).orElse(null);
        System.out.println("Animal supprimé, trouvé = " + (animal != null));

        // --- TP4 ---
        System.out.println("\nTP4");
        System.out.println("Test des requêtes SpeciesRepository");
        Species s = speciesRepository.findByCommonName("Chat").orElse(null);
        System.out.println("Espèce trouvée par nom commun 'Chat' : " + s);

        s = speciesRepository.findFirstByCommonNameContaining("Chat").orElse(null);
        System.out.println("Première espèce contenant 'Chat' : " + s);

        List<Species> speciesList = (List<Species>) speciesRepository.findAllByLatinNameContainingIgnoreCase("lus");
        System.out.println("Toutes les espèces contenant 'lus' dans le nom latin : ");
        speciesList.forEach(System.out::println);

        System.out.println("Test des requêtes PersonRepository");
        List<Person> personList = (List<Person>) personRepository.findAllByLastNameOrFirstName("Lamarque", "Bill");
        System.out.println("Personnes trouvées avec le nom de famille 'Lamarque' ou le prénom 'Bill' : ");
        personList.forEach(System.out::println);

        personList = (List<Person>) personRepository.findAllByAgeGreaterThanEqual(30);
        System.out.println("Personnes trouvées avec un âge supérieur ou égal à 30 : ");
        personList.forEach(System.out::println);

        System.out.println("\nAnimaux de l'espèce 'Chat' :");
        Species chat = speciesRepository.findByCommonName("Chat").orElse(null);
        if (chat != null) {
            List<Animal> animauxChats = (List<Animal>) animalRepository.findAllBySpecies(chat);
            animauxChats.forEach(System.out::println);
        } else {
            System.out.println("Espèce 'Chat' non trouvée.");
        }

        System.out.println("\nAnimaux dont la couleur est 'Brun' ou 'Blanc' :");
        List<String> couleurs = List.of("Brun", "Blanc");
        List<Animal> animauxCouleurs = (List<Animal>) animalRepository.findAllByColorIn(couleurs);
        animauxCouleurs.forEach(System.out::println);
    }
}
