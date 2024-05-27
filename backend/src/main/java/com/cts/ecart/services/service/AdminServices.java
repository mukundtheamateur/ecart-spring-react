package com.cts.ecart.services.service;

import com.cts.ecart.entity.Admin;
import com.cts.ecart.entity.Product;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminServices {
    Admin findAdminById(int id) throws NotFoundException;

    List<Admin> findAllAdmins();

    Admin save(Admin admin) throws AlreadyExistsException, NotFoundException;

    ResponseEntity<String> deleteById(int id) throws NotFoundException;
    
    Admin findAdminByEmail(String email) throws NotFoundException;
    
    List<Product> findAllProductsByAdminId(Integer id) throws NotFoundException;
}
