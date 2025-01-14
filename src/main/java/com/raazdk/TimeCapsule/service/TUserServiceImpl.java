package com.raazdk.TimeCapsule.service;

import com.raazdk.TimeCapsule.mappers.UserDTOMapper;
import com.raazdk.TimeCapsule.models.AppRoles;
import com.raazdk.TimeCapsule.models.Role;
import com.raazdk.TimeCapsule.models.TUser;
import com.raazdk.TimeCapsule.repository.RolesRepository;
import com.raazdk.TimeCapsule.repository.UserRepository;
import com.raazdk.TimeCapsule.security.request.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TUserServiceImpl implements TUserService{
    Logger logger = LoggerFactory.getLogger(TUserServiceImpl.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    UserDTOMapper mapper;

    @Autowired
    PasswordEncoder encoder;
    @Override
    public Optional<TUser> getUserByUserName(String username) {
        return userRepository.findByusername(username);
    }

    @Override
    public String createUser(RegisterRequest registerRequest) {

        try {
            TUser user = mapper.toUser(registerRequest);
            user.setPassword(encoder.encode(registerRequest.getPassword()));
            Role role = rolesRepository.findByRoleName(AppRoles.ROLE_USER)  // Fetch role from DB
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            user.setRole(role);
            userRepository.save(user);
            return ("User created with Id: "+user.getUserId());

        } catch (DataIntegrityViolationException e) {

            if (e.getCause().getMessage().toLowerCase().contains("username")) {
                return "Error: Username invalid.";
            } else if(e.getCause().getMessage().toLowerCase().contains("role")){
                return "Error : Error initializing roles";
            }
            return "Error: Data integrity Error";
        }catch (Exception e) {
            logger.error("Error saving user {}",TUserServiceImpl.class);
           return ("User creation failed with"+e.toString());
        }

    }


}
