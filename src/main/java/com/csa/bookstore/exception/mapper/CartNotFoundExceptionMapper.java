package com.csa.bookstore.exception.mapper;

import com.csa.bookstore.exception.CartNotFoundException;
import com.csa.bookstore.exception.ErrorResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Soshan Wijayarathne
 */
@Provider
public class CartNotFoundExceptionMapper implements ExceptionMapper<CartNotFoundException> {
    @Override
    public Response toResponse(CartNotFoundException e) {
        ErrorResponse error = new ErrorResponse("CART_NOT_FOUND", e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}
