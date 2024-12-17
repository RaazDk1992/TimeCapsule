package com.raazdk.TimeCapsule.service;

import com.raazdk.TimeCapsule.models.TUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface TUserService {

    Optional<TUser> getUserByUserName(String username);
}
