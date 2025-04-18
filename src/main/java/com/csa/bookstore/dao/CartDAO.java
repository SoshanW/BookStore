package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Cart;
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
}
