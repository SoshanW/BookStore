package com.csa.bookstore.resource;

import com.csa.bookstore.dao.BookDAO;
import com.csa.bookstore.dao.CartDAO;
import com.csa.bookstore.dao.CustomerDAO;
import com.csa.bookstore.entity.Cart;
import com.csa.bookstore.exception.OutOfStockException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Soshan Wijayarathne
 */

@Path("customer/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    private final CartDAO cartDAO = new CartDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final BookDAO bookDAO = new BookDAO();
    
    @POST
    @Path("/items")
    public Response addItem(@PathParam("customerId") int customerId, Cart cart){
       validateCustomer(customerId);
       validateBook(cart.getBookId());
       checkStock(cart.getBookId(), cart.getQuantity());
       
       cartDAO.addItem(customerId, cart.getBookId(), cart.getQuantity());
       bookDAO.getBookById(cart.getBookId())
              .setStockQuantity(bookDAO.getBookById(cart.getBookId()).getStockQuantity() - cart.getQuantity());
        return Response.ok().build();
    }
    
    @GET
    public Response getCart(@PathParam("customerId") int customerId){
        validateCustomer(customerId);
        return Response.ok(cartDAO.getCart(customerId)).build();
    }
    
    @PUT
    @Path("/item/{bookId}")
    public Response updateItem(@PathParam("customerId") int customerId,
            @PathParam("bookId") String bookId, 
            int newQuantity){
        validateCustomer(customerId);
        checkStock(bookId, newQuantity);
        
        cartDAO.updateItem(customerId, bookId, newQuantity);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/items/{bookId}")
    public Response removeItem(@PathParam("customerId") int customerId, 
                              @PathParam("bookId") String bookId) {
        validateCustomer(customerId);
        cartDAO.removeItem(customerId, bookId);
        return Response.noContent().build();
    }

    private void validateCustomer(int customerId) {
        customerDAO.getCustomerById(customerId);
    }

    private void validateBook(String bookId) {
        bookDAO.getBookById(bookId);
    }

    private void checkStock(String bookId, int quantity) {
        int stock = bookDAO.getBookById(bookId).getStockQuantity();
        if (stock < quantity) {
            throw new OutOfStockException("Only " + stock + " items available");
        }
    }
}
