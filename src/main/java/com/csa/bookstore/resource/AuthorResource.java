package com.csa.bookstore.resource;

import com.csa.bookstore.dao.AuthorDAO;
import com.csa.bookstore.dao.BookDAO;
import com.csa.bookstore.entity.Author;
import com.csa.bookstore.entity.Book;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
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
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    private final AuthorDAO authorDAO = new AuthorDAO();
    private final BookDAO bookDAO = new BookDAO();

    
    @POST
    public Response addAuthor(Author author, @Context UriInfo uriInfo){
        Author created = authorDAO.addAuthor(author);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(uri).entity(created).build();
    }
    
    @GET
    public Response getAllAuthors() {
        return Response.ok(authorDAO.getAllAuthors()).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        return Response.ok(authorDAO.getAuthorById(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        return Response.ok(authorDAO.updateAuthor(id, updatedAuthor)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        authorDAO.deleteAuthor(id);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") int id) {
        Author author = authorDAO.getAuthorById(id);
        List<Book> books = bookDAO.getAllBooks().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author.getName()))
                .collect(Collectors.toList());
        return Response.ok(books).build();
    }
}
