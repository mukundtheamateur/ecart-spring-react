package com.cts.ecart.services.impl;

import com.cts.ecart.entity.Cart;
import com.cts.ecart.entity.Product;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.repository.CartRepository;
import com.cts.ecart.repository.ProductRepository;
import com.cts.ecart.repository.UserRepository;
import com.cts.ecart.services.service.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServicesImpl implements CartServices {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartServicesImpl(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Cart saveCart(Cart cart) throws NotFoundException {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        if (cartRepository.existsById(cart.getId())) {
            throw new NotFoundException("Cart already exists with id: " + cart.getId());
        }
 
        // Check if product exists
        for (Product product : cart.getProducts()) {
            if (!productRepository.existsById(product.getId())) {
                throw new NotFoundException("Product with id: " + product.getId() + " does not exist");
            }
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


	@Override
	public Cart getCartByUserId(Integer userId) throws NotFoundException {
		Optional<Cart> cart = Optional.ofNullable(userRepository.findCartByUserId(userId));
		if(cart.isEmpty()) {
			throw new NotFoundException("Cart with this user does not exist ");
			
		}
		return cart.get();
	}
}