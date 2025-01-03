package com.raazdk.TimeCapsule.service;

import com.raazdk.TimeCapsule.models.TUser;
import com.raazdk.TimeCapsule.security.request.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface TUserService {

    Optional<TUser> getUserByUserName(String username);
    TUser createUser(RegisterRequest registerRequest);
}
