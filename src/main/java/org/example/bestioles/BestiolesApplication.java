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

         Animal animal2 = animalRepository.findById(animal.getId()).orElse(null);
        System.out.println("\nNouvel animal ajouté : " + animal2);

        // --- Suppression de l'animal ajouté ---
        System.out.println("\nSuppression du nouvel animal ajouté :");
        animalRepository.delete(animal);
        Animal animal3 = animalRepository.findById(animal.getId()).orElse(null);
        System.out.println("Animal supprimé, trouvé = " + (animal3 != null));

        // --- TP4 ---
        System.out.println("\nTP4");
        System.out.println("\nTest des requêtes SpeciesRepository");
        Species species2 = speciesRepository.findByCommonName("Chat").orElse(null);
        System.out.println("Espèce trouvée par nom commun 'Chat' : " + species2);

        Species species3 = speciesRepository.findFirstByCommonNameContaining("Chat").orElse(null);
        System.out.println("Première espèce contenant 'Chat' : " + species3);

        List<Species> speciesList = (List<Species>) speciesRepository.findAllByLatinNameContainingIgnoreCase("lus");
        System.out.println("Toutes les espèces contenant 'lus' dans le nom latin : ");
        speciesList.forEach(System.out::println);

        List<Species> speciesList2 = (List<Species>) speciesRepository.findAllOrderByCommonName();
        System.out.println("Toutes les espèces ordonnées par nom commun décroissant : ");
        speciesList2.forEach(System.out::println);

        List<Species> speciesList3 = (List<Species>) speciesRepository.findAllByCommonNamePattern("ch");
        System.out.println("Toutes les espèces dont le nom commun contient le pattern 'ch' : ");
        speciesList3.forEach(System.out::println);

        System.out.println("\nTest des requêtes PersonRepository");
        List<Person> personList = (List<Person>) personRepository.findAllByLastNameOrFirstName("Lamarque", "Bill");
        System.out.println("Personnes trouvées avec le nom de famille 'Lamarque' ou le prénom 'Bill' : ");
        personList.forEach(System.out::println);

        List<Person> personList2 = (List<Person>) personRepository.findAllByAgeGreaterThanEqual(30);
        System.out.println("Personnes trouvées avec un âge supérieur ou égal à 30 : ");
        personList2.forEach(System.out::println);

        List<Person> personList3 = (List<Person>) personRepository.findAllByAgeBetween(25, 35);
        System.out.println("Personnes trouvées avec un âge entre 25 et 35 : ");
        personList3.forEach(System.out::println);

        List<Person> personList4 = (List<Person>) personRepository.findAllByAnimalName("Max");
        System.out.println("Personnes possédant un animal nommé 'Max' : ");
        personList4.forEach(System.out::println);

        System.out.println("\nTest des requêtes AnimalRepository");
        System.out.println("Animaux de l'espèce 'Chat' :");
        Species chat = speciesRepository.findByCommonName("Chat").orElse(null);
        if (chat != null) {
            List<Animal> animauxChats = (List<Animal>) animalRepository.findAllBySpecies(chat);
            animauxChats.forEach(System.out::println);
        } else {
            System.out.println("Espèce 'Chat' non trouvée.");
        }

        System.out.println("Animaux dont la couleur est 'Brun' ou 'Blanc' :");
        List<String> couleurs = List.of("Brun", "Blanc");
        List<Animal> animauxCouleurs = (List<Animal>) animalRepository.findAllByColorIn(couleurs);
        animauxCouleurs.forEach(System.out::println);

        Long nombreMales = animalRepository.countBySex("M");
        System.out.println("Nombre d'animaux de sexe masculin : " + nombreMales);
        Animal animalCheck = animalRepository.findById(1).orElse(null);
        boolean appartenance = animalRepository.existsByAnimalBelongsToAnyPerson(animalCheck);
        System.out.println("L'animal avec l'ID 1 appartient-il à au moins une personne ? " + appartenance);
    }
}
