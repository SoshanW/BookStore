package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Cart;
import com.csa.bookstore.exception.CartNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Soshan Wijayarathne
 */
public class CartDAO {
    private static final Map<Integer, Map<String, Cart>> carts = new ConcurrentHashMap<>();
    
    public void addItem(int customerId, String bookId, int quantity){
        carts.compute(customerId, (k, v) -> {
            if (v == null) v = new ConcurrentHashMap<>();
            v.compute(bookId, (bk, item) -> 
                item == null ? new Cart(bookId, quantity) : new Cart(bk, item.getQuantity() + quantity));
            return v;
        });
    }
    
    public Map<String, Cart> getCart(int customerId){
        Map<String, Cart> cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart for customer "+customerId+" not found");
        }
        return cart;
    }
    
    public void updateItem(int customerId, String bookId, int quantity){
        carts.computeIfPresent(customerId, (k, v)->{
            v.computeIfPresent(bookId, (bk, item)->{
                item.setQuantity(quantity);
                return item;
            });
            return v;
        });
    }
    
    public void removeItem(int customerId, String bookId){
        carts.computeIfPresent(customerId, (k, v)->{
            v.remove(bookId);
            return v;
        });
    }
    
    public void clearCart(int customerId){
        carts.remove(customerId);
    }
}
