package com.cts.ecart.services.impl;

import com.cts.ecart.entity.Product;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.repository.ProductRepository;
import com.cts.ecart.services.service.ProductServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServicesImpl implements ProductServices {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServicesImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() throws NotFoundException {
        log.info("Fetching all cars");
        List<Product> products = productRepository.findAll();
        if(!products.isEmpty()) {
            log.info("all products fetched");
            return products;
        }
        log.error("Cars not found! NotFoundException");
        throw new NotFoundException("No Products exist!");
    }

    @Override
    public Product getProductById(Integer id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product saveProduct(Product product) throws NotFoundException, AlreadyExistsException {
        if (productRepository.existsById(product.getId())) {
            throw new AlreadyExistsException("Product already exists with id: " + product.getId());
        } else {
            return productRepository.save(product);
        }
    }

    @Override
    public void deleteProduct(Integer id) throws NotFoundException {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new NotFoundException("Product not found with id: " + id);
        }
    }

    @Override
    public Product getProductByName(String name) throws NotFoundException {
        Optional<Product> optionalProduct = productRepository.findByName(name);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new NotFoundException("Product not found with name: " + name);
        }
    }

    @Override
    public Product updateProduct(Product product, Integer id) throws NotFoundException {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        } else {
            throw new NotFoundException("Product not found with id: " + id);
        }
    }

}