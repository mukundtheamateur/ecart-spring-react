package com.cts.ecart.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ecart.entity.Booking;
import com.cts.ecart.entity.User;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.services.service.UserServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() throws NotFoundException {
        return new ResponseEntity<>(userServices.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) throws NotFoundException {
        User user = userServices.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping(value= "/create")
    public ResponseEntity<User> createUser(@RequestBody User user) throws AlreadyExistsException, NotFoundException {
        User updatedUser= userServices.saveUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) throws NotFoundException {
        ResponseEntity<String> response = userServices.deleteUser(id);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) throws NotFoundException {
        // Get the email of the currently authenticated user
        String currentEmail = email;
        log.info(currentEmail);
        return new ResponseEntity<>(userServices.getUserByEmail(email), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userUpdates) throws NotFoundException {
        User updatedUser = userServices.updateUser(userUpdates, id);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @GetMapping("/id/{id}/bookings")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Integer id) throws NotFoundException{
        
    	List<Booking> bookings = userServices.getBookingsByUserId(id);
    	if(bookings.isEmpty()) {
    		throw new NotFoundException("No bookings found for this user!!");
    	}
    	return new ResponseEntity<>(bookings, HttpStatus.OK);
        
    }
}
