package com.csa.bookstore.resource;

import com.csa.bookstore.dao.BookDAO;
import com.csa.bookstore.entity.Book;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Soshan Wijayarathne
 */

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    private static final Logger logger = Logger.getLogger(BookResource.class.getName());
    private final BookDAO bookDAO = new BookDAO();

    @POST
    public Response addBook(Book book, @Context UriInfo uriInfo){
        logger.log(Level.INFO, "POST /books - Adding book: {0}", book);
        Book addedBook = bookDAO.addBook(book);
        URI uri = uriInfo.getAbsolutePathBuilder().path(addedBook.getISBN()).build();
        logger.log(Level.INFO, "Book created with ISBN: {0}", addedBook.getISBN());
        return Response.created(uri).entity(addedBook).build();
    }

    @GET
    public Response getAllBooks(){
        logger.info("GET /books - Fetching all books");
        return Response.ok(bookDAO.getAllBooks()).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") String id){
        logger.log(Level.INFO, "GET /books/{0} - Fetching book by ID", id);
        return Response.ok(bookDAO.getBookById(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") String id, Book updatedBook){
        logger.log(Level.INFO, "PUT /books/{0} - Updating book: {1}", new Object[]{id, updatedBook});
        return Response.ok(bookDAO.updateBook(id, updatedBook)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") String id){
        logger.log(Level.INFO, "DELETE /books/{0} - Deleting book", id);
        bookDAO.deleteBook(id);
        return Response.noContent().build();
    }
}