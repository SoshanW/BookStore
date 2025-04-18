package com.csa.bookstore.exception;

/**
 *
 * @author Soshan Wijayarathne
 */
public class OutOfStockException extends RuntimeException{

    public OutOfStockException(String message) {
        super(message);
    }
}
