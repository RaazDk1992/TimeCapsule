package com.raazdk.TimeCapsule.repository;

import com.raazdk.TimeCapsule.models.TUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<TUser,Long> {
    Optional<TUser> findByusername(String username);
}
