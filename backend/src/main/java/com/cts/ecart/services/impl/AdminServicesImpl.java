package com.cts.ecart.services.impl;

import com.cts.ecart.constant.RoleType;
import com.cts.ecart.entity.Admin;
import com.cts.ecart.entity.Product;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.repository.AdminRepository;
import com.cts.ecart.repository.ProductRepository;
import com.cts.ecart.services.service.AdminServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminServicesImpl implements AdminServices {

    private final AdminRepository adminRepository;
    private final ProductRepository productRepository;
    @Autowired
    public AdminServicesImpl(AdminRepository adminRepository, ProductRepository productRepository) {
        this.adminRepository = adminRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Admin findAdminById(int id) throws NotFoundException{
        log.debug("Fetching admin with id: {}", id);
        return adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such Admin exists"));
    }

    @Override
    public List<Admin> findAllAdmins(){
        log.info("Fetching all admins");
        return adminRepository.findAll();
    }

    @Override
    public Admin save(Admin admin) throws AlreadyExistsException, NotFoundException {
        log.info("Checking if admin with email: {} exists or not", admin.getEmail());
        Optional<Admin> existingAdmin = adminRepository.findByEmail(admin.getEmail());
        if (existingAdmin.isPresent()) {
            log.error("Error occurred while saving admin: {}", admin);
            throw new AlreadyExistsException("Admin already exists with email: " + admin.getEmail());
        }

        // Save the user to the database
        admin.setRole(RoleType.ADMIN);
        Admin savedAdmin = adminRepository.save(admin);

        // If the user was not saved correctly, throw an exception
        if (savedAdmin == null) {
            throw new NotFoundException("The Admin could not be saved.");
        }

        return savedAdmin;
    }

    @Override
    public ResponseEntity<String> deleteById(int id) throws NotFoundException {
        Optional<Admin> getAdmin = adminRepository.findById(id);
        if (getAdmin.isPresent()) {
            adminRepository.delete(getAdmin.get());
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        }else{
            throw new NotFoundException("Admin does not exist with this id");
        }

    }

	@Override
	public Admin findAdminByEmail(String email) throws NotFoundException {
		Optional<Admin> admin = adminRepository.findByEmail(email);
		if(admin.isPresent()) {
			return admin.get();
		}
		else {
			throw new NotFoundException("Admin can not be found with this email id ");
		}
	}

	@Override
	public List<Product> findAllProductsByAdminId(Integer id) throws NotFoundException {
		log.info("find products by admin id");
		List<Product> products = productRepository.findAllByAdminId(id);
		if(products.isEmpty()) {
			throw new NotFoundException("products can not be found");
		}
		return products;
	}
}
