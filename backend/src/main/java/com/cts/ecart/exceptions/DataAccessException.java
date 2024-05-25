package com.cts.ecart.exceptions;

@SuppressWarnings("serial")
public class DataAccessException extends Exception{

    public DataAccessException(String message) {
        super(message);
    }
}