package com.csa.bookstore.exception;

/**
 *
 * @author Soshan Wijayarathne
 */
public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException(String message) {
        super(message);
    }
    
}
