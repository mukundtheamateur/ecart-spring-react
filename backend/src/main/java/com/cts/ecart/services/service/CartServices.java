package com.cts.ecart.services.service;

import com.cts.ecart.entity.Cart;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;

import java.util.List;

public interface CartServices {

    List<Cart> getCartsByUserId(Integer id) throws NotFoundException;

    Cart saveCart(Cart cart) throws NotFoundException, AlreadyExistsException;

    Cart getCartById(Integer id) throws NotFoundException;

    Cart updateCart(Cart cart, Integer id) throws NotFoundException;

    void deleteCart(Integer id) throws NotFoundException;

    List<Cart> getAllCarts() throws NotFoundException;
}
