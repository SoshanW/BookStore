package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Cart;
import com.csa.bookstore.exception.CartNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 *
 * @author Soshan Wijayarathne
 */
public class CartDAO {
    private static final Map<Integer, Map<String, Cart>> carts = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(CartDAO.class.getName());
    
    public void addItem(int customerId, String bookId, int quantity){
        carts.compute(customerId, (k, v) -> {
            if (v == null) v = new ConcurrentHashMap<>();
            v.compute(bookId, (bk, item) -> 
                item == null ? new Cart(bookId, quantity) : new Cart(bk, item.getQuantity() + quantity));
            return v;
        });
        logger.info("Added item to cart: customerId=" + customerId + ", bookId=" + bookId + ", quantity=" + quantity);
    }

    public Map<String, Cart> getCart(int customerId){
        Map<String, Cart> cart = carts.get(customerId);
        if (cart == null) {
            logger.warning("Cart not found for customer: " + customerId);
            throw new CartNotFoundException("Cart for customer "+customerId+" not found");
        }
        logger.info("Fetched cart for customer: " + customerId);
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
        logger.info("Updated item in cart: customerId=" + customerId + ", bookId=" + bookId + ", quantity=" + quantity);
    }

    public void removeItem(int customerId, String bookId){
        carts.computeIfPresent(customerId, (k, v)->{
            v.remove(bookId);
            return v;
        });
        logger.info("Removed item from cart: customerId=" + customerId + ", bookId=" + bookId);
    }

    public void clearCart(int customerId){
        carts.remove(customerId);
        logger.info("Cleared cart for customer: " + customerId);
    }
}
