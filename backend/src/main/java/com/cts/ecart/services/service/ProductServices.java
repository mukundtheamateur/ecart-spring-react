package com.cts.ecart.services.service;

import java.util.List;

import com.cts.ecart.entity.Product;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;

public interface ProductServices {

    List<Product> getAllProducts() throws NotFoundException;

    Product getProductById(Integer id) throws NotFoundException;

    Product saveProduct(Product product) throws NotFoundException, AlreadyExistsException;

    String deleteProduct(Integer id) throws NotFoundException;

    Product getProductByName(String name) throws NotFoundException;

    Product updateProduct(Product product) throws NotFoundException;

    List<Product> getProductsByAdminId(Integer adminId) throws NotFoundException;
}
