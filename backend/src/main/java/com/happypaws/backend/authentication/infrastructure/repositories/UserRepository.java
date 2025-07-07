package com.happypaws.backend.authentication.infrastructure.repositories;


import com.happypaws.backend.authentication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationCode(String verificationCode);

    @Query(value = """
        SELECT u.* FROM users u
        LEFT JOIN users_roles ur ON u.id = ur.user_id
        LEFT JOIN roles r ON r.id = ur.role_id
        WHERE r.name = 'OWNER'
          AND (
            6371 * acos(
              cos(radians(:lat)) * cos(radians(u.latitude)) *
              cos(radians(u.longitude) - radians(:lon)) +
              sin(radians(:lat)) * sin(radians(u.latitude))
            )
          ) < 3
        """, nativeQuery = true)
    List<User> findNearbyPetOwners(@Param("lat") double lat, @Param("lon") double lon);

    @Query(value = """
        SELECT u.* FROM users u
        LEFT JOIN users_roles ur ON u.id = ur.user_id
        LEFT JOIN roles r ON r.id = ur.role_id
        WHERE r.name = :roleName
        """, nativeQuery = true)
    List<User> findUsersByRole(@Param("roleName") String roleName);
}