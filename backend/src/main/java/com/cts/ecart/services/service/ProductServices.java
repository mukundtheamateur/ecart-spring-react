package com.cts.ecart.services.service;

import com.cts.ecart.entity.Product;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;

import java.util.List;

public interface ProductServices {

    List<Product> getAllProducts() throws NotFoundException;

    Product getProductById(Integer id) throws NotFoundException;

    Product saveProduct(Product product) throws NotFoundException, AlreadyExistsException;

    void deleteProduct(Integer id) throws NotFoundException;

    Product getProductByName(String name) throws NotFoundException;

    Product updateProduct(Product product, Integer id) throws NotFoundException;

}
