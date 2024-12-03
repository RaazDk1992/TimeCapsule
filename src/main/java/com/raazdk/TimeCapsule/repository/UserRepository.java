package com.raazdk.TimeCapsule.repository;

import com.raazdk.TimeCapsule.models.TUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<TUser,Long> {
}
