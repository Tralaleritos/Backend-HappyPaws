package com.happypaws.backend.offersmanager.domain.offers;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    @ManyToMany(mappedBy = "services", fetch = FetchType.LAZY)
    private List<Offer> offers = new ArrayList<>();

    public static Service Create(String name, String description) {
        return Service.builder().name(name).description(description).build();
    }
}
