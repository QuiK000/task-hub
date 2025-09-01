package com.dev.quikkkk.repository;

import com.dev.quikkkk.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String roleName);
}
