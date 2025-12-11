package org.example.bestioles.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "person", schema = "bestioles")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "firstname", nullable = false, length = 50)
    private String firstName;

    @Column(name = "lastname", nullable = false, length = 50)
    private String lastName;

    @Column(name = "login", nullable = false, length = 50)
    private String login;

    @Column(name = "mdp", nullable = false, length = 100)
    private String mdp;

    @ColumnDefault("1")
    @Column(name = "active", nullable = false)
    private Byte active;

    @ManyToMany
    @JoinTable(name = "person_role", schema = "bestioles", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(mappedBy = "persons")
    private Set<Animal> animals;

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", login='" + login + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id != null && id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}