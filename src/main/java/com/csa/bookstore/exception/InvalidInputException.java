package com.csa.bookstore.exception;

/**
 *
 * @author Soshan Wijayarathne
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}