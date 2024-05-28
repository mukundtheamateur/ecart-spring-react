package com.cts.ecart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ecart.entity.Product;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.services.service.ProductServices;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

	private final ProductServices productServices;
	/**
	 * @param productServices
	 */
	@Autowired
	public ProductController(ProductServices productServices) {
		this.productServices = productServices;
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() throws NotFoundException{
		log.info("fetch all the products controller gets called");
		List<Product> products = productServices.getAllProducts();
		if(products.isEmpty()) {
			throw new NotFoundException("No products found");
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping(value="/id/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id) throws NotFoundException{
		log.info("controller called for fetch product by product id");
		Product product = productServices.getProductById(id);
		if(product==null) {
			throw new NotFoundException("product with this id can not be found");
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
		
	}
	
	@PostMapping(value="/create")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) throws AlreadyExistsException, NotFoundException{
		
		log.info("creating product controller is called");
		Product updatedProduct = productServices.saveProduct(product);

		return new ResponseEntity<Product>(updatedProduct, HttpStatus.CREATED);
	}
	
	
	@PutMapping(value="/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws NotFoundException{
		log.info("update product controller is called");
		return new ResponseEntity<>(productServices.updateProduct(product), HttpStatus.OK);
		
	}
	
	@DeleteMapping(value="/delete/id/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) throws NotFoundException{
		log.info("deleting the product");
		return new ResponseEntity<>(productServices.deleteProduct(productId), HttpStatus.OK);
	}
}
