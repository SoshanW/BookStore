package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Book;
import com.csa.bookstore.exception.BookNotFoundException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
    
    
    public Book addBook(Book book){
        String id = String.valueOf(idCounter.incrementAndGet());
        book.setISBN(id);
        books.put(id, book);
        return book;
    }
    
    public List<Book> getAll(){
        return new ArrayList<>(books.values());
    }
    
    public Book getById(String id){
        Book book = books.get(id);
        if(book == null) {
            throw new BookNotFoundException("Book "+id+" not found");
        }
        return book;
    }
}
