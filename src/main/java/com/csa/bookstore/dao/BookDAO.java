package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Book;
import com.csa.bookstore.exception.BookNotFoundException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Soshan Wijayarathne
 */
public class BookDAO {
    
    /**
     * Added ConcurrentHashMap instead of ArrayList is for efficient key based access (O(1)), Thread safety and Dynamic Scaling
     * Added AtomicInteger to ensure thread-safe increment for generating unique id's
     */
    private static final Map<String, Book> books = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1000);
    private static final Logger logger = Logger.getLogger(BookDAO.class.getName());

    public BookDAO() {
    }

    public Book addBook(Book book){
        String id = String.valueOf(idCounter.incrementAndGet());
        book.setISBN(id);
        books.put(id, book);
        logger.log(Level.INFO, "Added book: {0}", book);
        return book;
    }

    public List<Book> getAllBooks(){
        logger.info("Fetching all books");
        return new ArrayList<>(books.values());
    }

    public Book getBookById(String id){
        Book book = books.get(id);
        if(book == null) {
            logger.log(Level.WARNING, "Book not found: {0}", id);
            throw new BookNotFoundException("Book "+id+" not found");
        }
        logger.log(Level.INFO, "Fetched book: {0}", book);
        return book;
    }

    public Book updateBook(String id, Book updatedBook){
        if(!books.containsKey(id)){
            logger.log(Level.WARNING, "Attempted update on missing book: {0}", id);
            throw new BookNotFoundException("Cannot update. Book with ID "+id+" not found");
        }
        updatedBook.setISBN(id);
        books.put(id, updatedBook);
        logger.log(Level.INFO, "Updated book: {0}", updatedBook);
        return updatedBook;
    }

    public void deleteBook(String id){
        if(!books.containsKey(id)){
            logger.log(Level.WARNING, "Attempted delete on missing book: {0}", id);
            throw new BookNotFoundException("Cannot delete. Book with ID "+id+" not found");
        }
        books.remove(id);
        logger.log(Level.INFO, "Deleted book with ID: {0}", id);
    }
}
