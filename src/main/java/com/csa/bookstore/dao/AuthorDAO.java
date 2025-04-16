package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Author;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Soshan Wijayarathne
 */
public class AuthorDAO {
    private static final Map<Integer, Author> authors = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(2000);
    
    public Author addAuthor(Author author){
        int id = idCounter.incrementAndGet();
        author.setId(id);
        authors.put(id, author);
        return author;
    }
}
