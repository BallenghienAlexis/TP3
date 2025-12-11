package org.example.bestioles.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "species", schema = "bestioles")
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "common_name", nullable = false, length = 50)
    private String commonName;

    @Column(name = "latin_name", nullable = false, length = 200)
    private String latinName;

    @OneToMany(mappedBy = "species", fetch = FetchType.EAGER)
    private List<Animal> animals;

    @Override
    public String toString() {
        return "Species{" + "id=" + id + ", commonName='" + commonName + '\'' + ", latinName='" + latinName + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return id != null && id.equals(species.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}