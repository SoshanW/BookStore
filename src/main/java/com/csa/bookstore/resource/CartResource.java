package com.csa.bookstore.resource;

import com.csa.bookstore.dao.BookDAO;
import com.csa.bookstore.dao.CartDAO;
import com.csa.bookstore.dao.CustomerDAO;
import com.csa.bookstore.entity.Book;
import com.csa.bookstore.entity.Cart;
import com.csa.bookstore.exception.OutOfStockException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
    private static final Logger logger = Logger.getLogger(CartResource.class.getName());
    private final CartDAO cartDAO = new CartDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final BookDAO bookDAO = new BookDAO();

    @POST
    @Path("/items")
    public Response addItem(@PathParam("customerId") int customerId, Cart cart){
        logger.log(Level.INFO, "POST /customer/{0}/cart/items - Adding item: {1}", new Object[]{customerId, cart});
        validateCustomer(customerId);
        validateBook(cart.getBookId());
        checkStock(cart.getBookId(), cart.getQuantity());
        cartDAO.addItem(customerId, cart.getBookId(), cart.getQuantity());
        bookDAO.getBookById(cart.getBookId())
               .setStockQuantity(bookDAO.getBookById(cart.getBookId()).getStockQuantity() - cart.getQuantity());
        logger.log(Level.INFO, "Item added to cart for customer {0}: {1}", new Object[]{customerId, cart});
        return Response.ok().build();
    }

    @GET
    public Response getCart(@PathParam("customerId") int customerId){
        logger.log(Level.INFO, "GET /customer/{0}/cart - Fetching cart", customerId);
        validateCustomer(customerId);
        Map<String, Cart> cart = cartDAO.getCart(customerId);
        Map<String, Cart> cartWithTitles = cart.entrySet().stream()
            .collect(Collectors.toMap(
                entry -> bookDAO.getBookById(entry.getKey()).getTitle(),
                Map.Entry::getValue
            ));
        return Response.ok(cartWithTitles).build();
    }

    @PUT
    @Path("/item/{bookId}")
    public Response updateItem(@PathParam("customerId") int customerId,
                              @PathParam("bookId") String bookId,
                              int newQuantity) {
        logger.log(Level.INFO, "PUT /customer/{0}/cart/item/{1} - Updating quantity to {2}", new Object[]{customerId, bookId, newQuantity});
        validateCustomer(customerId);
        int oldQuantity = cartDAO.getCart(customerId).get(bookId).getQuantity();
        int diff = newQuantity - oldQuantity;
        int stock = bookDAO.getBookById(bookId).getStockQuantity();
        if (stock < diff) {
            logger.log(Level.WARNING, "Not enough stock for book {0}: requested diff={1}, available={2}", new Object[]{bookId, diff, stock});
            throw new OutOfStockException("Only " + stock + " items available");
        }
        cartDAO.updateItem(customerId, bookId, newQuantity);
        bookDAO.getBookById(bookId).setStockQuantity(stock - diff);
        logger.log(Level.INFO, "Cart item updated for customer {0}, book {1}", new Object[]{customerId, bookId});
        return Response.ok().build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeItem(@PathParam("customerId") int customerId,
                              @PathParam("bookId") String bookId) {
        logger.log(Level.INFO, "DELETE /customer/{0}/cart/items/{1} - Removing item", 
            new Object[]{customerId, bookId});
        
        validateCustomer(customerId);
        
        // Get quantity before removal
        Cart item = cartDAO.getCart(customerId).get(bookId);
        if(item != null) {
            int quantityToRestore = item.getQuantity();
            
            // Remove from cart first
            cartDAO.removeItem(customerId, bookId);
            
            // Restore stock
            Book book = bookDAO.getBookById(bookId);
            book.setStockQuantity(book.getStockQuantity() + quantityToRestore);
            logger.log(Level.INFO, "Restored {0} units of book {1}", 
                new Object[]{quantityToRestore, bookId});
        }
        
        return Response.noContent().build();
    }

    @DELETE
    @Path("/items")
    public Response clearCart(@PathParam("customerId") int customerId) {
        logger.log(Level.INFO, "DELETE /customer/{0}/cart/items - Clearing cart", customerId);
        validateCustomer(customerId);
        
        // Get all items before clearing
        Map<String, Cart> cartItems = cartDAO.getCart(customerId);
        
        // Restore stock for all items
        cartItems.forEach((bookId, cartItem) -> {
            Book book = bookDAO.getBookById(bookId);
            book.setStockQuantity(book.getStockQuantity() + cartItem.getQuantity());
            logger.log(Level.INFO, "Restored {0} units of book {1}", 
                new Object[]{cartItem.getQuantity(), bookId});
        });
        
        // Clear cart after restoring stock
        cartDAO.clearCart(customerId);
        
        return Response.noContent().build();
    }

    private void validateCustomer(int customerId) {
        logger.log(Level.FINE, "Validating customer {0}", customerId);
        customerDAO.getCustomerById(customerId);
    }

    private void validateBook(String bookId) {
        logger.log(Level.FINE, "Validating book {0}", bookId);
        bookDAO.getBookById(bookId);
    }

    private void checkStock(String bookId, int quantity) {
        int stock = bookDAO.getBookById(bookId).getStockQuantity();
        if (stock < quantity) {
            logger.log(Level.WARNING, "Not enough stock for book {0}: requested={1}, available={2}", new Object[]{bookId, quantity, stock});
            throw new OutOfStockException("Only " + stock + " items available");
        }
    }
}