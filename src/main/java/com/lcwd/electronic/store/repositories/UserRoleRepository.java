package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoles, Integer> {
    Optional<UserRoles> findByRoleName(String roleName);
}

