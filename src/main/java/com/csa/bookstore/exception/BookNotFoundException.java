package com.csa.bookstore.exception;

/**
 *
 * @author Soshan Wijayarathne
 */
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message){
        super(message);
    }
}
