package com.csa.bookstore.exception.mapper;

import com.csa.bookstore.exception.AuthorNotFoundException;
import com.csa.bookstore.exception.ErrorResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Soshan Wijayarathne
 */
@Provider
public class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException>{

    @Override
    public Response toResponse(AuthorNotFoundException e) {
        ErrorResponse error = new ErrorResponse("AUTHOR_NOT_FOUND", e.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .build();
    }
    
}
