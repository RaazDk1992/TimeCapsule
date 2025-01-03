package com.raazdk.TimeCapsule.service;

import com.raazdk.TimeCapsule.mappers.UserDTOMapper;
import com.raazdk.TimeCapsule.models.TUser;
import com.raazdk.TimeCapsule.repository.UserRepository;
import com.raazdk.TimeCapsule.security.request.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TUserServiceImpl implements TUserService{
    Logger logger = LoggerFactory.getLogger(TUserServiceImpl.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDTOMapper mapper;

    @Autowired
    PasswordEncoder encoder;
    @Override
    public Optional<TUser> getUserByUserName(String username) {
        return userRepository.findByusername(username);
    }

    @Override
    public TUser createUser(RegisterRequest registerRequest) {

        TUser user = mapper.toUser(registerRequest);
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        System.out.println("Mappedd..."+user.toString());
        System.out.println(registerRequest.toString());
        return null;
    }


}
