package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Book;
import com.csa.bookstore.exception.BookNotFoundException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
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
        logger.info("Added book: " + book);
        return book;
    }

    public List<Book> getAllBooks(){
        logger.info("Fetching all books");
        return new ArrayList<>(books.values());
    }

    public Book getBookById(String id){
        Book book = books.get(id);
        if(book == null) {
            logger.warning("Book not found: " + id);
            throw new BookNotFoundException("Book "+id+" not found");
        }
        logger.info("Fetched book: " + book);
        return book;
    }

    public Book updateBook(String id, Book updatedBook){
        if(!books.containsKey(id)){
            logger.warning("Attempted update on missing book: " + id);
            throw new BookNotFoundException("Cannot update. Book with ID "+id+" not found");
        }
        updatedBook.setISBN(id);
        books.put(id, updatedBook);
        logger.info("Updated book: " + updatedBook);
        return updatedBook;
    }

    public void deleteBook(String id){
        if(!books.containsKey(id)){
            logger.warning("Attempted delete on missing book: " + id);
            throw new BookNotFoundException("Cannot delete. Book with ID "+id+" not found");
        }
        books.remove(id);
        logger.info("Deleted book with ID: " + id);
    }
}
