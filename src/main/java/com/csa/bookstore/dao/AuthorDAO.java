package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Author;
import com.csa.bookstore.exception.AuthorNotFoundException;
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
public class AuthorDAO {
    private static final Map<Integer, Author> authors = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(2000);
    private static final Logger logger = Logger.getLogger(AuthorDAO.class.getName());

    public AuthorDAO() {
    }
    
    public Author addAuthor(Author author){
        int id = idCounter.incrementAndGet();
        author.setId(id);
        authors.put(id, author);
        logger.log(Level.INFO, "Added author: {0}", author);
        return author;
    }

    public List<Author> getAllAuthors(){
        logger.info("Fetching all authors");
        return new ArrayList<>(authors.values());
    }

    public Author getAuthorById(int id){
        Author author = authors.get(id);
        if(author == null) {
            logger.log(Level.WARNING, "Author not found: {0}", id);
            throw new AuthorNotFoundException("Author "+id+" not found");
        }
        logger.log(Level.INFO, "Fetched author: {0}", author);
        return author;
    }

    public Author updateAuthor(int id, Author updatedAuthor){
        if(!authors.containsKey(id)){
            logger.log(Level.WARNING, "Attempted update on missing author: {0}", id);
            throw new AuthorNotFoundException("Cannot update. Author with ID "+id+" not found");
        }
        updatedAuthor.setId(id);
        authors.put(id, updatedAuthor);
        logger.log(Level.INFO, "Updated author: {0}", updatedAuthor);
        return updatedAuthor;
    }

    public void deleteAuthor(int id){
        if(!authors.containsKey(id)){
            logger.log(Level.WARNING, "Attempted delete on missing author: {0}", id);
            throw new AuthorNotFoundException("Cannot delete. Author with ID "+id+" not found");
        }
        authors.remove(id);
        logger.log(Level.INFO, "Deleted author with ID: {0}", id);
    }
}
