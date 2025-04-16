package com.csa.bookstore.resource;

import com.csa.bookstore.dao.BookDAO;
import com.csa.bookstore.entity.Book;
import java.net.URI;
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
    
    private final BookDAO bookDAO = new BookDAO();
    
    @POST
    public Response addBook(Book book, @Context UriInfo uriInfo){
        Book addedBook = bookDAO.addBook(book);
        URI uri = uriInfo.getAbsolutePathBuilder().path(addedBook.getISBN()).build();
        return Response.created(uri).entity(addedBook).build();
    }
    
    @GET
    public Response getAllBooks(){
        return Response.ok(bookDAO.getAllBooks()).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") String id){
        return Response.ok(bookDAO.getBookById(id)).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id")String id, Book updatedBook){
        return Response.ok(bookDAO.updateBook(id, updatedBook)).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id")String id){
        bookDAO.deleteBook(id);
        return Response.noContent().build();
    }
}
