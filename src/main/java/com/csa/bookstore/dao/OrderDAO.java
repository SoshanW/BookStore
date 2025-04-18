package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Order;
import com.csa.bookstore.exception.OrderNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Soshan Wijayarathne
 */
public class OrderDAO {
    private static final Map<Integer, Order> orders = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(4000);
    
    public Order createOrder(int customerId, Map<String, Integer> items, double total){
        Order order = new Order();
        order.setId(idCounter.incrementAndGet());
        order.setCustomerId(customerId);
        order.setItems(items);
        order.setTotalPrice(total);
        order.setOrderDate(new Date());
        order.setStatus("PROCESSING");
        orders.put(order.getId(), order);
        return order;
    }
    
    public List<Order> gerOrderByCustomer(int customerId){
        List<Order> result = new ArrayList<>();
        for(Order order: orders.values()){
            if(order.getCustomerId() == customerId){
                result.add(order);
            }
        }
        return result;
    }
    
    public Order getOrderById(int orderId){
        Order order = orders.get(orderId);
        if(order == null){
            throw new OrderNotFoundException("Order "+orderId+ " not found");
        }
        return order;
    }
}
