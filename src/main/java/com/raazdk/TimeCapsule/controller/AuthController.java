package com.raazdk.TimeCapsule.controller;

import com.raazdk.TimeCapsule.dto.LoginRequestDto;
import com.raazdk.TimeCapsule.dto.LoginResponse;
import com.raazdk.TimeCapsule.models.Role;
import com.raazdk.TimeCapsule.security.jwt.JwtUtils;
import com.raazdk.TimeCapsule.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto){

        Authentication authentication;
        try {

            authentication = manager
                    .authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(),requestDto.getPassword()));

        } catch (Exception e) {
            Map<String,Object> response= new HashMap<>();
            response.put("status","fail");
            response.put("error","Bad Credentials");
            System.out.println(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            //throw new RuntimeException(e);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails details = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateJwtFromUsername(details);
        List<String> roles = details.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
        LoginResponse successresponse = new LoginResponse(details.getUsername(),roles,jwtToken);


        return new ResponseEntity<>(successresponse,HttpStatus.OK);
    }

}