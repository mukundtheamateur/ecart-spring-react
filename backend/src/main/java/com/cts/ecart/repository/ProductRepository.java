package com.cts.ecart.repository;

import com.cts.ecart.constant.CategoryType;
import com.cts.ecart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);

    List<Product> findByCategory(CategoryType categoryType);
    
    List<Product> findAllByAdminId(Integer id);

	List<Product> findProductsByAdminId(Integer adminId);
}
