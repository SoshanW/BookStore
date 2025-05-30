package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Order;
import com.csa.bookstore.exception.OrderNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Soshan Wijayarathne
 */
public class OrderDAO {
    private static final Map<Integer, Order> orders = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(4000);
    private static final Logger logger = Logger.getLogger(CartDAO.class.getName());
    
    public Order createOrder(int customerId, Map<String, Integer> items, double total){
        Order order = new Order();
        order.setId(idCounter.incrementAndGet());
        order.setCustomerId(customerId);
        order.setItems(items);
        order.setTotalPrice(total);
        order.setOrderDate(new Date());
        order.setStatus("PROCESSING");
        orders.put(order.getId(), order);
        logger.log(Level.INFO, "Created order: {0}", order);
        return order;
    }

    public List<Order> getOrdersByCustomer(int customerId) {
        List<Order> result = new ArrayList<>();
        for(Order order : orders.values()) {
            if(order.getCustomerId() == customerId) {
                result.add(order);
            }
        }
        if(result.isEmpty()) {
            throw new OrderNotFoundException("No orders found for customer: " + customerId);
        }
        return result;
    }


    public Order getOrderById(int orderId){
        Order order = orders.get(orderId);
        if(order == null){
            logger.log(Level.WARNING, "Order not found: {0}", orderId);
            throw new OrderNotFoundException("Order "+orderId+ " not found");
        }
        logger.log(Level.INFO, "Fetched order: {0}", order);
        return order;
    }
}
