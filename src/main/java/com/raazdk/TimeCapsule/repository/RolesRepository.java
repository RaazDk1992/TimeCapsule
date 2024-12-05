package com.raazdk.TimeCapsule.repository;

import com.raazdk.TimeCapsule.models.AppRoles;
import com.raazdk.TimeCapsule.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(AppRoles role);
}
