package com.cts.ecart.services.service;

import com.cts.ecart.entity.Cart;
import com.cts.ecart.exceptions.NotFoundException;

import java.util.List;

public interface CartServices {

    Cart saveCart(Cart cart) throws NotFoundException;

    void deleteCart(Integer id) throws NotFoundException;

    Cart getCartById(Integer id) throws NotFoundException;

    List<Cart> getAllCarts();
}
