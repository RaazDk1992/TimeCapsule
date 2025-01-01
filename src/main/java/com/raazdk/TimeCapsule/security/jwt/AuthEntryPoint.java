package com.raazdk.TimeCapsule.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPoint  implements AuthenticationEntryPoint {
    Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("An error Occured{}",authException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final Map<String,Object> message = new HashMap<>();
        message.put("message","An error ocurred");
        message.put("status",HttpServletResponse.SC_UNAUTHORIZED);
        message.put("path",request.getServletPath());
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),message);

    }


    private String exactError(AuthenticationException ex){
        final String DEFAULT_MESSAGE = "Full authentication needed";
        if(ex.getMessage().contains("Bad Credentials")){
            return "Invalid username or password.";
        }else if( ex.getMessage().contains("JWT Expired")){
            return "Your session has expired. Please log in again.";

        }
        return  DEFAULT_MESSAGE;
    }
}
