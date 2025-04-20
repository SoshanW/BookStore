package com.csa.bookstore.resource;

import com.csa.bookstore.dao.AuthorDAO;
import com.csa.bookstore.dao.BookDAO;
import com.csa.bookstore.entity.Book;
import com.csa.bookstore.exception.InvalidInputException;
import java.net.URI;
import java.util.List;
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
    private final AuthorDAO authorDAO = new AuthorDAO();

    @POST
    public Response addBook(Book book, @Context UriInfo uriInfo) {
        // Validate input fields
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()
            || book.getPrice() <= 0
            || book.getStockQuantity() < 0) {
            throw new InvalidInputException("Title, positive price, and non-negative stockQuantity are required");
        }
        
        // Validate author exists
        try {
            authorDAO.getAuthorById(book.getAuthorId());
        } catch (Exception e) {
            throw new InvalidInputException("Invalid author ID: " + book.getAuthorId());
        }

        logger.log(Level.INFO, "POST /books - Adding book: {0}", book);
        Book addedBook = bookDAO.addBook(book);
        
        // Set author name for response
        addedBook.setAuthorName(authorDAO.getAuthorById(book.getAuthorId()).getName());

        URI uri = uriInfo.getAbsolutePathBuilder().path(addedBook.getISBN()).build();
        return Response.created(uri).entity(addedBook).build();
    }

    @GET
    public Response getAllBooks() {
        logger.info("GET /books - Fetching all books");
        List<Book> books = bookDAO.getAllBooks();
        books.forEach(book -> {
            try {
                book.setAuthorName(authorDAO.getAuthorById(book.getAuthorId()).getName());
            } catch (Exception e) {
                book.setAuthorName("Unknown Author");
            }
        });
        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") String id) {
        logger.log(Level.INFO, "GET /books/{0} - Fetching book by ID", id);
        Book book = bookDAO.getBookById(id);
        try {
            book.setAuthorName(authorDAO.getAuthorById(book.getAuthorId()).getName());
        } catch (Exception e) {
            book.setAuthorName("Unknown Author");
        }
        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") String id, Book updatedBook) {
        // Validate author exists if ID is being updated
        if (updatedBook.getAuthorId() != 0) {
            try {
                authorDAO.getAuthorById(updatedBook.getAuthorId());
            } catch (Exception e) {
                throw new InvalidInputException("Invalid author ID: " + updatedBook.getAuthorId());
            }
        }
        
        logger.log(Level.INFO, "PUT /books/{0} - Updating book: {1}", new Object[]{id, updatedBook});
        Book book = bookDAO.updateBook(id, updatedBook);
        
        // Update author name for response
        try {
            book.setAuthorName(authorDAO.getAuthorById(book.getAuthorId()).getName());
        } catch (Exception e) {
            book.setAuthorName("Unknown Author");
        }
        
        return Response.ok(book).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") String id) {
        logger.log(Level.INFO, "DELETE /books/{0} - Deleting book", id);
        bookDAO.deleteBook(id);
        return Response.noContent().build();
    }
}