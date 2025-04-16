package com.csa.bookstore.exception.mapper;

import com.csa.bookstore.exception.ErrorResponse;
import com.csa.bookstore.exception.OrderNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Soshan Wijayarathne
 */
@Provider
public class OrderNotFoundExceptionMapper implements ExceptionMapper<OrderNotFoundException>{

    @Override
    public Response toResponse(OrderNotFoundException e) {
        ErrorResponse error = new ErrorResponse("CUSTOMER_NOT_FOUND", e.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .build();
    }
    
}
