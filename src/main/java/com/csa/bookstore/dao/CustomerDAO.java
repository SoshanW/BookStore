package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Customer;
import com.csa.bookstore.exception.CustomerNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
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
        logger.info("Added customer: " + customer);
        return customer;
    }

    public List<Customer> getAllCustomers(){
        logger.info("Fetching all customers");
        return new ArrayList<>(customers.values());
    }

    public Customer getCustomerById(int id){
        Customer customer = customers.get(id);
        if (customer == null){
            logger.warning("Customer not found: " + id);
            throw new CustomerNotFoundException("Customer "+id+" not found");
        }
        logger.info("Fetched customer: " + customer);
        return customer;
    }

    public Customer updateCustomer(int id, Customer updatedCustomer){
        if (!customers.containsKey(id)) {
            logger.warning("Attempted update on missing customer: " + id);
            throw new CustomerNotFoundException("Cannot Update. Customer "+id+" not found");
        }
        updatedCustomer.setId(id);
        customers.put(id, updatedCustomer);
        logger.info("Updated customer: " + updatedCustomer);
        return updatedCustomer;
    }

    public void deleteCustomer(int id) {
        if (!customers.containsKey(id)) {
            logger.warning("Attempted delete on missing customer: " + id);
            throw new CustomerNotFoundException("Cannot delete. Customer " + id + " not found");
        }
        customers.remove(id);
        logger.info("Deleted customer with ID: " + id);
    }
}
