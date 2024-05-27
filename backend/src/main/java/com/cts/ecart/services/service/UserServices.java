package com.cts.ecart.services.service;

import com.cts.ecart.entity.Booking;
import com.cts.ecart.entity.Cart;
import com.cts.ecart.entity.User;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServices {
    List<User> getUsers() throws NotFoundException;;
    User getUserById(Integer id) throws NotFoundException;
    User saveUser(User user) throws AlreadyExistsException, NotFoundException;;
    ResponseEntity<String> deleteUser(Integer id) throws NotFoundException;
    User getUserByEmail(String email) throws NotFoundException;
    User updateUser(User user, Integer id) throws NotFoundException;
    List<Booking> getBookingsByUserId(Integer id) throws NotFoundException;
    Cart getCartByUserId(Integer id) throws NotFoundException;

}
