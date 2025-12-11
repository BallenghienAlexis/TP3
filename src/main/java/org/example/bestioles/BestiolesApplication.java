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
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
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
        System.out.println("TP3 - CRUD Operations");
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

        System.out.println("\nAjout d'un nouvel animal :");
        Animal animal = new Animal();
        animal.setName("Leo");
        animal.setSex("M");
        animal.setColor("Brun");
        animal.setSpecies(speciesRepository.findByCommonName("Chien").orElse(null));
        animalRepository.save(animal);

         Animal animal2 = animalRepository.findById(animal.getId()).orElse(null);
        System.out.println("\nNouvel animal ajouté : " + animal2);

        System.out.println("\nSuppression du nouvel animal ajouté :");
        animalRepository.delete(animal);
        Animal animal3 = animalRepository.findById(animal.getId()).orElse(null);
        System.out.println("Animal supprimé, trouvé = " + (animal3 != null));

        System.out.println("\nTP4/TP5 - Test des requêtes personnalisées");
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

        System.out.println("\nTP6 - Test des méthodes personnalisées du repository");
        long countBeforeGen = personRepository.count();
        System.out.println("Nombre de personnes avant génération : " + countBeforeGen);
        personRepository.generatePersons(3);
        long countAfterGen = personRepository.count();
        System.out.println("Nombre de personnes après génération : " + countAfterGen);
        if (countAfterGen >= countBeforeGen + 3) {
            System.out.println("Génération de personnes : OK");
        } else {
            System.out.println("Génération de personnes : ECHEC");
        }

        long countBeforeDelete = personRepository.count();
        personRepository.deletePersonsWithoutAnimals();
        long countAfterDelete = personRepository.count();
        System.out.println("Nombre de personnes après suppression des personnes sans animaux : " + countAfterDelete);
        if (countAfterDelete < countBeforeDelete) {
            System.out.println("Suppression des personnes sans animaux : OK");
        } else {
            System.out.println("Suppression des personnes sans animaux : ECHEC");
        }

        System.out.println("TP6-BIS");
        List<Person> personToAdd = new ArrayList<Person>();
        Person p1 = new Person();
        p1.setFirstName("Bill");
        p1.setLastName("Bache");
        p1.setLogin("bill.Bache");
        p1.setMdp("password1");
        p1.setAge(28);
        p1.setActive((byte)1);
        personToAdd.add(p1);

        Person p2 = new Person();
        p2.setFirstName("Boul");
        p2.setLastName("Bache");
        p2.setLogin("Boul.Bache");
        p2.setMdp("password2");
        p2.setAge(35);
        p2.setActive((byte)1);
        personToAdd.add(p2);

        personRepository.saveAll(personToAdd);

        List<Person> personList6 = (List<Person>) personRepository.findAllByLastNameOrFirstName("Bache", null);
        System.out.println("Personnes trouvées avec le nom de famille 'Bache' : ");
        personList6.forEach(System.out::println);
        personRepository.deleteByLastName("Bache");
        List<Person> personList7 = (List<Person>) personRepository.findAllByLastNameOrFirstName("Bache", null);
        System.out.println("Personnes trouvées avec le nom de famille 'Bache' après suppression : ");
        personList7.forEach(System.out::println);
        List<Person> restoredPersons = new ArrayList<>();
        for (Person p : personList6) {
            Person newPerson = new Person();
            newPerson.setFirstName(p.getFirstName());
            newPerson.setLastName(p.getLastName());
            newPerson.setLogin(p.getLogin());
            newPerson.setMdp(p.getMdp());
            newPerson.setAge(p.getAge());
            newPerson.setActive(p.getActive());
            restoredPersons.add(newPerson);
        }
        personRepository.saveAll(restoredPersons);
    }
}
