package com.happypaws.backend.petmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.happypaws.backend.authentication.domain.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Species species;
    private String breed;
    private int age;
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private User owner;

    public static Pet Create(String name, String description, Species species, String breed, int age, User owner, String imgUrl) {
        return Pet.builder()
                .name(name)
                .description(description)
                .species(species)
                .breed(breed)
                .age(age)
                .imgUrl(imgUrl)
                .owner(owner)
                .build();
    }

    public void Update(String name, String description, Species species, String breed, int age, String imgUrl) {
        this.name = name;
        this.description = description;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.imgUrl = imgUrl;
    }
}
