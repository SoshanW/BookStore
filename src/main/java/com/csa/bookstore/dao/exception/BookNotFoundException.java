package com.csa.bookstore.dao.exception;

/**
 *
 * @author Soshan Wijayarathne
 */
class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message){
        super(message);
    }
}
