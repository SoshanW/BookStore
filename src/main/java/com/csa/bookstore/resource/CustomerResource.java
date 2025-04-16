/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csa.bookstore.resource;

import com.csa.bookstore.dao.CustomerDAO;
import com.csa.bookstore.entity.Customer;
import com.csa.bookstore.exception.InvalidInputException;
import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author user
 */

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    
    private final CustomerDAO customerDAO = new CustomerDAO();
    
    @POST
    public Response addCustomer(Customer customer, @Context UriInfo uriInfo){
        validateCustomerInput(customer);
        Customer addedCustomer = customerDAO.addCustomer(customer);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(addedCustomer.getId())).build();
        return Response.created(uri).entity(addedCustomer).build();
    }

    private void validateCustomerInput(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty() ||
            customer.getEmail() == null || !customer.getEmail().contains("@") ||
            customer.getPassword() == null || customer.getPassword().length() < 4) {
            throw new InvalidInputException("Invalid customer data. Name, valid email, and password (min 4 chars) required");
        }
    }
    
}
