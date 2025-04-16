package com.csa.bookstore.entity;

/**
 *
 * @author Soshan Wijayarathne
 */
public class Cart {
    private String bookId;
    private int quantity;

    public Cart() {
    }

    public Cart(String bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" + "bookId=" + bookId + ", quantity=" + quantity + '}';
    }
    
    
}
