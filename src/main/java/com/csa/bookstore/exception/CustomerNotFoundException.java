/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csa.bookstore.exception;

/**
 *
 * @author user
 */
public class CustomerNotFoundException extends Throwable {
    public CustomerNotFoundException(String message){
        super(message);
    }
}
