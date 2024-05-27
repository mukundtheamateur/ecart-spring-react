package com.cts.ecart.repository;

import com.cts.ecart.entity.Cart;
import com.cts.ecart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Cart findCartByUserId(@Param("userId") int userId);

}
