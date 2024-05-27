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

import com.cts.ecart.entity.Admin;
import com.cts.ecart.entity.Product;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.services.service.AdminServices;
import com.cts.ecart.services.service.ProductServices;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminServices adminServices;
	private final ProductServices productServices;
	@Autowired
	public AdminController(AdminServices adminServices, ProductServices productServices) {
		this.adminServices = adminServices;
		this.productServices = productServices;
	}
	@GetMapping
	public ResponseEntity<List<Admin>> getAllAdmin() throws NotFoundException{
		log.info("Fetch all admins");
		List<Admin> admins = adminServices.findAllAdmins();
		if(admins.isEmpty()) {
			throw new NotFoundException("Admins not found");
		}
		return new ResponseEntity<List<Admin>>(admins, HttpStatus.OK); 
	}
	

    @GetMapping("/id/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") Integer id) throws NotFoundException {
        Admin admin = adminServices.findAdminById(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }


    @PostMapping(value= "/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) throws AlreadyExistsException, NotFoundException {
        Admin updatedAdmin= adminServices.save(admin);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.CREATED);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable("id") Integer id) throws NotFoundException {
        
    	ResponseEntity<String> response = adminServices.deleteById(id);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Admin deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable("email") String email) throws NotFoundException {
    	log.info("finding admin with this email id");
    	Admin admin = adminServices.findAdminByEmail(email);
        return new ResponseEntity<Admin>(admin, HttpStatus.OK);
    }



    @GetMapping("/id/{id}/products")
    public ResponseEntity<List<Product>> getAdminProducts(@PathVariable Integer id) throws NotFoundException{
        
    	List<Product> products = productServices.getProductsByAdminId(id);
    	if(products.isEmpty()) {
    		throw new NotFoundException("No products found for this user!!");
    	}
    	return new ResponseEntity<>(products, HttpStatus.OK);
        
    }
}
