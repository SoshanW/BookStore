package com.csa.bookstore.exception.mapper;

import com.csa.bookstore.exception.ErrorResponse;
import com.csa.bookstore.exception.OutOfStockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Soshan Wijayarathne
 */
@Provider
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {
    @Override
    public Response toResponse(OutOfStockException e) {
        ErrorResponse error = new ErrorResponse("OUT_OF_STOCK",e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build();
    }
}