package com.csa.bookstore.resource;

import com.csa.bookstore.dao.CustomerDAO;
import com.csa.bookstore.entity.Customer;
import com.csa.bookstore.exception.InvalidInputException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Soshan Wijayarathne
 */

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private static final Logger logger = Logger.getLogger(CustomerResource.class.getName());
    private final CustomerDAO customerDAO = new CustomerDAO();

    @POST
    public Response addCustomer(Customer customer, @Context UriInfo uriInfo){
        logger.info("POST /customers - Request to add customer: " + customer);
        try {
            validateCustomerInput(customer);
            Customer addedCustomer = customerDAO.addCustomer(customer);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(addedCustomer.getId())).build();
            logger.info("Customer created with ID: " + addedCustomer.getId());
            return Response.created(uri).entity(addedCustomer).build();
        } catch (InvalidInputException e) {
            logger.warning("Invalid customer input: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error adding customer", e);
            throw e;
        }
    }

    @GET
    public Response getAllCustomers(){
        logger.info("GET /customers - Fetching all customers");
        return Response.ok(customerDAO.getAllCustomers()).build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id){
        logger.info("GET /customers/" + id + " - Fetching customer by ID");
        return Response.ok(customerDAO.getCustomerById(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Customer updatedCustomer) {
        logger.info("PUT /customers/" + id + " - Updating customer: " + updatedCustomer);
        return Response.ok(customerDAO.updateCustomer(id, updatedCustomer)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        logger.info("DELETE /customers/" + id + " - Deleting customer");
        customerDAO.deleteCustomer(id);
        logger.info("Customer deleted with ID: " + id);
        return Response.noContent().build();
    }

    private void validateCustomerInput(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty() ||
            customer.getEmail() == null || !customer.getEmail().contains("@") ||
            customer.getPassword() == null || customer.getPassword().length() < 4) {
            logger.warning("Validation failed for customer input: " + customer);
            throw new InvalidInputException("Invalid customer data. Name, valid email, and password (min 4 chars) required");
        }
    }
}
