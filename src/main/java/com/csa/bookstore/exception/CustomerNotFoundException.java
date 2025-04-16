package com.csa.bookstore.exception;

/**
 *
 * @author Soshan Wijayarathne
 */
public class CustomerNotFoundException extends Throwable {
    public CustomerNotFoundException(String message){
        super(message);
    }
}
