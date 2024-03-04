package com.bfs.shopauth.controller;

import com.bfs.shopauth.domain.LoginRequest;
import com.bfs.shopauth.domain.LoginResponse;
import com.bfs.shopauth.security.AuthUserDetail;
import com.bfs.shopauth.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController

public class LoginController {

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    private JwtProvider jwtProvider;
    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }


    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public LoginResponse login(@RequestBody LoginRequest request){

        Authentication authentication;

        //Try to authenticate the user using the username and password
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Provided credential is invalid.");
        }

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object
        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> "admin".equals(authority.getAuthority()));

        //Returns the token as a response to the frontend/postman
        return LoginResponse.builder()
                .message("Welcome " + authUserDetail.getUsername())
                .success(true)
                .token(token)
                .permission(isAdmin? "admin": "user")
                .build();
    }




}
