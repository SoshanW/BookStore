package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Customer;
import com.csa.bookstore.exception.CustomerNotFoundException;
import java.util.ArrayList;
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
public class CustomerDAO {
    private static final Map<Integer, Customer> customers = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(3000);
    private static final Logger logger = Logger.getLogger(CartDAO.class.getName());
    
    public Customer addCustomer(Customer customer){
        int id = idCounter.incrementAndGet();
        customer.setId(id);
        customers.put(id, customer);
        logger.log(Level.INFO, "Added customer: {0}", customer);
        return customer;
    }

    public List<Customer> getAllCustomers(){
        logger.info("Fetching all customers");
        return new ArrayList<>(customers.values());
    }

    public Customer getCustomerById(int id){
        Customer customer = customers.get(id);
        if (customer == null){
            logger.log(Level.WARNING, "Customer not found: {0}", id);
            throw new CustomerNotFoundException("Customer "+id+" not found");
        }
        logger.log(Level.INFO, "Fetched customer: {0}", customer);
        return customer;
    }

    public Customer updateCustomer(int id, Customer updatedCustomer){
        if (!customers.containsKey(id)) {
            logger.log(Level.WARNING, "Attempted update on missing customer: {0}", id);
            throw new CustomerNotFoundException("Cannot Update. Customer "+id+" not found");
        }
        updatedCustomer.setId(id);
        customers.put(id, updatedCustomer);
        logger.log(Level.INFO, "Updated customer: {0}", updatedCustomer);
        return updatedCustomer;
    }

    public void deleteCustomer(int id) {
        if (!customers.containsKey(id)) {
            logger.log(Level.WARNING, "Attempted delete on missing customer: {0}", id);
            throw new CustomerNotFoundException("Cannot delete. Customer " + id + " not found");
        }
        customers.remove(id);
        logger.log(Level.INFO, "Deleted customer with ID: {0}", id);
    }
}
