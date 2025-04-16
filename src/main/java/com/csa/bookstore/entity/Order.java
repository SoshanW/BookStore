package com.csa.bookstore.entity;

import java.util.Date;
import java.util.Map;

/**
 *
 * @author Soshan Wijayarathne
 */
public class Order {
    private int id;
    private int customerId;
    private Map<String, Integer> items;
    private double totalPrice;
    private Date orderDate;
    private String status;

    public Order() {
    }

    public Order(int id, int customerId, Map<String, Integer> items, double totalPrice, Date orderDate, String status) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", customerId=" + customerId + ", items=" + items + ", totalPrice=" + totalPrice + ", orderDate=" + orderDate + ", status=" + status + '}';
    }
    
    
}
