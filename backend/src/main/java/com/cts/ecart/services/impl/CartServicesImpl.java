package com.cts.ecart.services.impl;

import com.cts.ecart.entity.Cart;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.repository.CartRepository;
import com.cts.ecart.services.service.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServicesImpl implements CartServices {

    private final CartRepository cartRepository;

    @Autowired
    public CartServicesImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public Cart saveCart(Cart cart) throws NotFoundException {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        if (cartRepository.existsById(cart.getId())) {
            throw new NotFoundException("Cart already exists with id: " + cart.getId());
        }

        return cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Integer id) throws NotFoundException {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
        } else {
            throw new NotFoundException("Cart not found with id: " + id);
        }
    }

    @Override
    public Cart getCartById(Integer id) throws NotFoundException {
        return cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Cart not found with id: " + id));
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}