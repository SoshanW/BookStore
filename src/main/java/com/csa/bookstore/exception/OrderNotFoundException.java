package com.csa.bookstore.exception;

/**
 *
 * @author Soshan Wijayarathne
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}