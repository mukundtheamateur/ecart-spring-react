package com.cts.ecart.handler;

import java.util.HashMap;
import java.util.Map;

import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.BadRequestException;
import com.cts.ecart.exceptions.DataAccessException;
import com.cts.ecart.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            NotFoundException.class,
            DataAccessException.class
    })
    public ResponseEntity<Map<String,Object>> handleNotFoundException(Exception e){
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.NOT_FOUND.value());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handleAlreadyExistsException(Exception e){
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.CONFLICT.value());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String,Object>> handleBadRequestException(Exception e){
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
