package com.csa.bookstore.resource;

import com.csa.bookstore.dao.BookDAO;
import com.csa.bookstore.dao.CartDAO;
import com.csa.bookstore.dao.CustomerDAO;
import com.csa.bookstore.dao.OrderDAO;
import com.csa.bookstore.entity.Cart;
import com.csa.bookstore.entity.Order;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Soshan Wijayarathne
 */

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    private static final Logger logger = Logger.getLogger(OrderResource.class.getName());
    private final OrderDAO orderDAO = new OrderDAO();
    private final CartDAO cartDAO = new CartDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final BookDAO bookDAO = new BookDAO();

    @POST
    public Response createOrder(@PathParam("customerId") int customerId) {
        logger.info("POST /customers/" + customerId + "/orders - Creating order for customer");
        try {
            customerDAO.getCustomerById(customerId);
            Map<String, Cart> cartItems = cartDAO.getCart(customerId);

            Map<String, Integer> itemQuantities = cartItems.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().getQuantity()
                ));

            double total = calculateTotal(itemQuantities);
            Order order = orderDAO.createOrder(customerId, itemQuantities, total);
            cartDAO.clearCart(customerId);
            logger.info("Order created for customer " + customerId + " with order ID: " + order.getId());
            return Response.ok(order).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating order for customer " + customerId, e);
            throw e;
        }
    }

    @GET
    public Response getOrdersByCustomerId(@PathParam("customerId") int customerId) {
        logger.info("GET /customers/" + customerId + "/orders - Fetching orders for customer");
        customerDAO.getCustomerById(customerId);
        return Response.ok(orderDAO.gerOrderByCustomer(customerId)).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderByOrderId(@PathParam("customerId") int customerId,
                                      @PathParam("orderId") int orderId) {
        logger.info("GET /customers/" + customerId + "/orders/" + orderId + " - Fetching order by ID");
        customerDAO.getCustomerById(customerId);
        return Response.ok(orderDAO.getOrderById(orderId)).build();
    }

    private double calculateTotal(Map<String, Integer> items) {
        double total = items.entrySet().stream()
                .mapToDouble(entry ->
                    bookDAO.getBookById(entry.getKey()).getPrice() * entry.getValue())
                .sum();
        logger.fine("Calculated order total: " + total);
        return total;
    }
}