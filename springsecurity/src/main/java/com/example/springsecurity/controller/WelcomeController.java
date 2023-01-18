package com.example.springsecurity.controller;

import com.example.springsecurity.model.AuthRequest;
import com.example.springsecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @Autowired
    private JwtUtil     jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/")
    public String welcome(){
        return "Welcome to javatechie !!";
    }
    @PostMapping("/authentication")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        }
        catch(Exception ex){
            throw new Exception("Username/passwor is invalid");

        }
        return jwtUtil.generateToken(authRequest.getUsername());

    }
}
