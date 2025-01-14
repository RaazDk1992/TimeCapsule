package com.raazdk.TimeCapsule.service;

import com.raazdk.TimeCapsule.models.TUser;
import com.raazdk.TimeCapsule.security.request.RegisterRequest;

import java.util.Optional;

public interface TUserService {

    Optional<TUser> getUserByUserName(String username);
    String createUser(RegisterRequest registerRequest);
}
