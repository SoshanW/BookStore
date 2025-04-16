package com.csa.bookstore.exception.mapper;

import com.csa.bookstore.exception.ErrorResponse;
import com.csa.bookstore.exception.InvalidInputException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Soshan Wijayarathne
 */
@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException>{

    @Override
    public Response toResponse(InvalidInputException e) {
        ErrorResponse error = new ErrorResponse("INVALID_INPUT", e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build();
    }
    
}
