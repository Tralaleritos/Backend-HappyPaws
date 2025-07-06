package com.happypaws.backend.authentication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.happypaws.backend.petmanager.domain.Pet;
import com.happypaws.backend.shared.domain.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    private String imgUrl;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "double precision default -77.0428")
    private double longitude;

    @Column(columnDefinition = "double precision default -12.0464")
    private double latitude;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Pet> pets = new ArrayList<>();

    private boolean enabled;

    private String verificationCode;

    @Column(name = "verification_expiration")
    private LocalDateTime verificationExpiration;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getUserName() { return username; }

    public void update(
            String username,
            String email,
            String phoneNumber,
            String imgUrl
    ) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imgUrl = imgUrl;
    }

    public void updateLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean isCaregiver() {
        return this.getRoles().stream()
                .anyMatch(r -> r.getName().equals("CAREGIVER"));
    }
}
