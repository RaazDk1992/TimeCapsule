package com.raazdk.TimeCapsule.service;

import com.raazdk.TimeCapsule.models.TUser;
import com.raazdk.TimeCapsule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TUserServiceImpl implements TUserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public Optional<TUser> getUserByUserName(String username) {
        return userRepository.findByusername(username);
    }
}
