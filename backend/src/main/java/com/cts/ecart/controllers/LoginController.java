package com.cts.ecart.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ecart.entity.User;
import com.cts.ecart.repository.UserRepository;
import com.cts.ecart.security.AuthenticationRequest;
import com.cts.ecart.security.AuthenticationResponse;
import com.cts.ecart.security.JwtUtil;
import com.cts.ecart.security.MyUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/login")
public class LoginController {
 
    @Autowired
    private AuthenticationManager authenticationManager;
 
    @Autowired
    private JwtUtil jwtUtil;
 
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    
    @Autowired
     private UserRepository userRepository;
    
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }

        final UserDetails userDetails;
        try {
            userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }

        final String jwt = jwtUtil.generateToken(userDetails);

        res.setHeader("jwt", jwt);

        return ResponseEntity.ok(new AuthenticationResponse("logged in successfully"));
    }

    
    @GetMapping("/load/user")
    public ResponseEntity<?> loadUser(HttpServletRequest req){
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            System.out.println("Username  "+ username);
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            if (username != null && jwtUtil.validateToken(jwt, userDetails)) {
                Optional<User> optionalUser = userRepository.findByEmail(username);
                if (optionalUser.isPresent()) {
                    return ResponseEntity.ok(optionalUser.get());
                }
                else {
                	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                	
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid JWT token");
    }

}