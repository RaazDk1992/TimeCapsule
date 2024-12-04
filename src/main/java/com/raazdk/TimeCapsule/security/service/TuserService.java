package com.raazdk.TimeCapsule.security.service;

import com.raazdk.TimeCapsule.models.TUser;
import com.raazdk.TimeCapsule.repository.RolesRepository;
import com.raazdk.TimeCapsule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TuserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TUser user = userRepository.findByusername(username).orElseThrow(()->new UsernameNotFoundException(""));
        return TuserDetails.build(user);
    }
}
