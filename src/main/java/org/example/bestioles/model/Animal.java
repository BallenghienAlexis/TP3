package org.example.bestioles.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "animal", schema = "bestioles")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "sex", nullable = false)
    private String sex;

    @ManyToOne(optional = false)
    @JoinColumn(name = "species_id", nullable = false)
    private Species species;

    @ManyToMany
    @JoinTable(name = "person_animals", schema = "bestioles", joinColumns = @JoinColumn(name = "animals_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> persons;

    @Override
    public String toString() {
        return "Animal{" + "id=" + id + ", name='" + name + '\'' + ", color='" + color + '\'' + ", sex='" + sex + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id != null && id.equals(animal.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}