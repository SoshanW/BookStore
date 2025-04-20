package com.csa.bookstore.resource;

import com.csa.bookstore.dao.AuthorDAO;
import com.csa.bookstore.dao.BookDAO;
import com.csa.bookstore.entity.Author;
import com.csa.bookstore.entity.Book;
import com.csa.bookstore.exception.InvalidInputException;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final Logger logger = Logger.getLogger(AuthorResource.class.getName());

    
     @POST
    public Response addAuthor(Author author, @Context UriInfo uriInfo){
        
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("Author name is required.");
        }
        logger.log(Level.INFO, "POST /authors - Adding author: {0}", author);
        Author created = authorDAO.addAuthor(author);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        logger.log(Level.INFO, "Author created with ID: {0}", created.getId());
        return Response.created(uri).entity(created).build();
    }

    @GET
    public Response getAllAuthors() {
        logger.info("GET /authors - Fetching all authors");
        return Response.ok(authorDAO.getAllAuthors()).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        logger.log(Level.INFO, "GET /authors/{0} - Fetching author by ID", id);
        return Response.ok(authorDAO.getAuthorById(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        logger.log(Level.INFO, "PUT /authors/{0} - Updating author: {1}", new Object[]{id, updatedAuthor});
        return Response.ok(authorDAO.updateAuthor(id, updatedAuthor)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        logger.log(Level.INFO, "DELETE /authors/{0} - Deleting author", id);
        authorDAO.deleteAuthor(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") int id) {
        logger.log(Level.INFO, "GET /authors/{0}/books - Fetching books by author", id);
        Author author = authorDAO.getAuthorById(id);
        List<Book> books = bookDAO.getAllBooks().stream()
                .filter(book -> book.getAuthorName().equalsIgnoreCase(author.getName()))
                .collect(Collectors.toList());
        return Response.ok(books).build();
    }
}
