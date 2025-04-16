package com.csa.bookstore.exception;

/**
 *
 * @author Soshan Wijayarathne
 */
public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
