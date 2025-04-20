package com.csa.bookstore.resource;

import com.csa.bookstore.dao.BookDAO;
import com.csa.bookstore.dao.CartDAO;
import com.csa.bookstore.dao.CustomerDAO;
import com.csa.bookstore.entity.Cart;
import com.csa.bookstore.exception.OutOfStockException;
import java.util.Map;
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
        logger.info("POST /customer/" + customerId + "/cart/items - Adding item: " + cart);
        validateCustomer(customerId);
        validateBook(cart.getBookId());
        checkStock(cart.getBookId(), cart.getQuantity());
        cartDAO.addItem(customerId, cart.getBookId(), cart.getQuantity());
        bookDAO.getBookById(cart.getBookId())
               .setStockQuantity(bookDAO.getBookById(cart.getBookId()).getStockQuantity() - cart.getQuantity());
        logger.info("Item added to cart for customer " + customerId + ": " + cart);
        return Response.ok().build();
    }

    @GET
    public Response getCart(@PathParam("customerId") int customerId){
        logger.info("GET /customer/" + customerId + "/cart - Fetching cart");
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
        logger.info("PUT /customer/" + customerId + "/cart/item/" + bookId + " - Updating quantity to " + newQuantity);
        validateCustomer(customerId);
        int oldQuantity = cartDAO.getCart(customerId).get(bookId).getQuantity();
        int diff = newQuantity - oldQuantity;
        int stock = bookDAO.getBookById(bookId).getStockQuantity();
        if (stock < diff) {
            logger.warning("Not enough stock for book " + bookId + ": requested diff=" + diff + ", available=" + stock);
            throw new OutOfStockException("Only " + stock + " items available");
        }
        cartDAO.updateItem(customerId, bookId, newQuantity);
        bookDAO.getBookById(bookId).setStockQuantity(stock - diff);
        logger.info("Cart item updated for customer " + customerId + ", book " + bookId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeItem(@PathParam("customerId") int customerId,
                              @PathParam("bookId") String bookId) {
        logger.info("DELETE /customer/" + customerId + "/cart/items/" + bookId + " - Removing item");
        validateCustomer(customerId);
        cartDAO.removeItem(customerId, bookId);
        logger.info("Item removed from cart for customer " + customerId + ", book " + bookId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/items")
    public Response clearCart(@PathParam("customerId") int customerId) {
        logger.info("DELETE /customer/" + customerId + "/cart/items - Clearing cart");
        validateCustomer(customerId);
        cartDAO.clearCart(customerId);
        logger.info("Cart cleared for customer " + customerId);
        return Response.noContent().build();
    }

    private void validateCustomer(int customerId) {
        logger.fine("Validating customer " + customerId);
        customerDAO.getCustomerById(customerId);
    }

    private void validateBook(String bookId) {
        logger.fine("Validating book " + bookId);
        bookDAO.getBookById(bookId);
    }

    private void checkStock(String bookId, int quantity) {
        int stock = bookDAO.getBookById(bookId).getStockQuantity();
        if (stock < quantity) {
            logger.warning("Not enough stock for book " + bookId + ": requested=" + quantity + ", available=" + stock);
            throw new OutOfStockException("Only " + stock + " items available");
        }
    }
}