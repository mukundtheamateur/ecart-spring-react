package com.cts.ecart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ecart.entity.Cart;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.services.service.CartServices;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/cart")
public class CartController {

    private final CartServices cartServices;

    @Autowired
    public CartController(CartServices cartServices) {
        this.cartServices = cartServices;
    }

    @PostMapping
    public ResponseEntity<Cart> saveCart(@RequestBody Cart cart) throws NotFoundException {
        log.info("cart controller is running");
    	Cart savedCart = cartServices.saveCart(cart);
        return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Integer id) throws NotFoundException {
        cartServices.deleteCart(id);
        return new ResponseEntity<>("Cart Deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Integer id) throws NotFoundException {
        Cart cart = cartServices.getCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping("/userId/{id}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Integer id) throws NotFoundException {
        Cart cart = cartServices.getCartByUserId(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartServices.getAllCarts();
        if(carts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }
}

