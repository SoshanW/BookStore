package com.csa.bookstore.exception;

/**
 *
 * @author Soshan Wijayarathne
 */
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message){
        super(message);
    }
}
