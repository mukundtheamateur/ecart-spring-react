package com.cts.ecart.services.impl;

import com.cts.ecart.entity.Cart;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.services.service.CartServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartServicesImpl implements CartServices {

    @Override
    public List<Cart> getCartsByUserId(Integer id) throws NotFoundException {
        return List.of();
    }

    @Override
    public Cart saveCart(Cart cart) throws NotFoundException, AlreadyExistsException {
        return null;
    }

    @Override
    public Cart getCartById(Integer id) throws NotFoundException {
        return null;
    }

    @Override
    public Cart updateCart(Cart cart, Integer id) throws NotFoundException {
        return null;
    }

    @Override
    public void deleteCart(Integer id) throws NotFoundException {

    }

    @Override
    public List<Cart> getAllCarts() throws NotFoundException {
        return List.of();
    }
}
