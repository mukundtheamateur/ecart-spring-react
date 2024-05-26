package com.cts.ecart.controllers;


import com.cts.ecart.entity.User;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.services.service.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        System.out.println(".................................."+currentEmail);
        log.info(currentEmail);
        // Check if the requested email matches the email of the currently authenticated user
        if (!email.equals(currentEmail)) {
            throw new AccessDeniedException("You are not authorized to access this data.");
        }

        return new ResponseEntity<>(userServices.getUserByEmail(email), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userUpdates) throws NotFoundException, RoleIdNotFoundException {
        User updatedUser = userServices.updateUser(id, userUpdates);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRoleId(@PathVariable Integer role) {
        try {
            List<User> users = userServices.getUsersByRoleId(role);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoUserFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{id}/bookings")
    public List<Bookings> getUserBookings(@PathVariable Integer id) throws NotFoundException{
        return userServices.getBookingsByUserId(id);
    }
}
