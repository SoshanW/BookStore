package com.csa.bookstore.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Soshan Wijayarathne
 */

@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException>{

    @Override
    public Response toResponse(BookNotFoundException e) {
        ErrorResponse error = new ErrorResponse("BOOK_NOT_FOUND", e.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .build();
    }
    
}
