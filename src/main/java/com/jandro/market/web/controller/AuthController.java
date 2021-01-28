package com.jandro.market.web.controller;

import com.jandro.market.domain.dto.AuthenticationRequest;
import com.jandro.market.domain.dto.AuthenticationResponse;
import com.jandro.market.domain.service.DefaultUserDetailsService;
import com.jandro.market.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DefaultUserDetailsService defaultUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> obtainPairToken(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails userDetails = defaultUserDetailsService.loadUserByUsername(request.getUsername());
            String jwt = jwtUtil.obtainPairToken(userDetails);
            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
