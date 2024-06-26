package com.cts.ecart.repository;

import com.cts.ecart.entity.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findCartById(Integer id);

//	Optional<Cart> findCartByUserId(Integer userId);
}
