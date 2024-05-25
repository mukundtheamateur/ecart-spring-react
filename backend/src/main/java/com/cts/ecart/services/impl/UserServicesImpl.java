package com.cts.ecart.services.impl;

import com.cts.ecart.constant.RoleType;
import com.cts.ecart.entity.Cart;
import com.cts.ecart.entity.User;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.repository.CartRepository;
import com.cts.ecart.repository.UserRepository;
import com.cts.ecart.services.service.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Autowired
    public UserServicesImpl(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }


    @Override
    public List<User> getUsers() throws NotFoundException {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) throws NotFoundException {
        log.debug("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such user exists"));
    }
    @Override
    public User saveUser(User user) throws AlreadyExistsException, NotFoundException {
        log.info("Checking if user with email: {} exists or not", user.getEmail());
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            log.error("Error occurred while saving user: {}", user);
            throw new AlreadyExistsException("User already exists with email: " + user.getEmail());
        }

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // If the user was not saved correctly, throw an exception
        if (savedUser == null) {
            throw new NotFoundException("The user could not be saved.");
        }

        return savedUser;
    }

    @Override
    public ResponseEntity<String> deleteUser(Integer id) throws NotFoundException {
        Optional<User> getUser = userRepository.findById(id);
        if (getUser.isPresent()) {
            userRepository.delete(getUser.get());
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        }else{
            throw new NotFoundException("User does not exist with this id");
        }

    }

    @Override
    public User getUserByEmail(String email) throws NotFoundException {
        log.debug("Fetching user with email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User does not exist with this email"));
    }

    @Override
    public User updateUser(User userUpdates, Integer id) throws NotFoundException {
        log.info("Updating user: {}", userUpdates);
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(userUpdates.getEmail() != null ? userUpdates.getEmail() : user.getEmail());
            user.setCreatedBy(userUpdates.getCreatedBy() != null ? userUpdates.getCreatedBy() : user.getCreatedBy());
            user.setUpdatedBy(userUpdates.getUpdatedBy() != null ? userUpdates.getUpdatedBy() : user.getUpdatedBy());
            user.setBirthday(userUpdates.getBirthday() != null ? userUpdates.getBirthday() : user.getBirthday());
            user.setUsername(userUpdates.getUsername() != null ? userUpdates.getUsername() : user.getUsername());
            user.setPassword(userUpdates.getPassword() != null ? userUpdates.getPassword() : user.getPassword());
            user.setProfession(userUpdates.getProfession() != null ? userUpdates.getProfession() : user.getProfession());
            user.setRole(userUpdates.getRole() != null ? RoleType.valueOf("USER") : user.getRole());
            User updatedUser = userRepository.save(user);
            log.info("User updated successfully: {}", updatedUser);
            return updatedUser;

        }
        throw new NotFoundException("User not found!");
    }

    @Override
    public List<Cart> getCartsByUserId(Integer id) throws NotFoundException {
        log.info("fetching carts with user id : {}", id);
        List<Cart> carts = cartRepository.findByUserId(id);
        if(carts.isEmpty()) {
            throw new NotFoundException("No cart found for user id: " + id);
        }
        log.info("bookings fetched");
        return carts;
    }
}
