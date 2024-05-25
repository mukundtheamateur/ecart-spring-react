package com.cts.ecart.exceptions;

@SuppressWarnings("serial")
public class AlreadyExistsException extends Exception{

    public AlreadyExistsException(String message) {
        super(message);
    }

}
