package com.cts.ecart.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.ecart.entity.Product;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.repository.ProductRepository;
import com.cts.ecart.services.service.ProductServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServicesImpl implements ProductServices {

    private final ProductRepository productRepository;
//    private final AdminRepository adminRepository;

    @Autowired
    public ProductServicesImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
//        this.adminRepository = adminRepository;
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
    public String deleteProduct(Integer id) throws NotFoundException {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return "product deleted successfully";
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
    public Product updateProduct(Product productDetails) throws NotFoundException {
        return productRepository.findById(productDetails.getId())
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setPrice(productDetails.getPrice());
                    product.setDescription(productDetails.getDescription());
                    product.setImageUrl(productDetails.getImageUrl());
                    product.setCategory(productDetails.getCategory());
                    product.setQuantity(productDetails.getQuantity());
                    product.setAdmin(productDetails.getAdmin());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productDetails.getId()));
    }

	@Override
	public List<Product> getProductsByAdminId(Integer adminId) throws NotFoundException {
		log.info("fetch all the products by admin id");
		List<Product> products = productRepository.findProductsByAdminId(adminId);
		if(products.isEmpty()) {
			throw new NotFoundException("product with this adminId can not be found");
		}
		return products;
	}




}