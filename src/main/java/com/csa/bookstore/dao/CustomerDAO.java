package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Customer;
import com.csa.bookstore.exception.CustomerNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Soshan Wijayarathne
 */
public class CustomerDAO {
    private static final Map<Integer, Customer> customers = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(3000);
    
    public Customer addCustomer(Customer customer){
        int id = idCounter.incrementAndGet();
        customer.setId(id);
        customers.put(id, customer);
        return customer;
    }
    
    public List<Customer> getAllCustomers(){
        return new ArrayList<>(customers.values());
    }
    
    public Customer getCustomerBCustomerById(int id){
        Customer customer = customers.get(id);
        if (customer == null){
            throw new CustomerNotFoundException("Customer "+id+" not found");
        }
        return customer;
    }
    
    public Customer updateCustomer(int id, Customer updatedCustomer){
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException("Cannot Update. Customer "+id+" not found");
        }
        updatedCustomer.setId(id);
        customers.put(id, updatedCustomer);
        return updatedCustomer;
    }
    
    public void deleteCustomer(int id) {
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException("Cannot delete. Customer " + id + " not found");
        }
        customers.remove(id);
    }
}
}
