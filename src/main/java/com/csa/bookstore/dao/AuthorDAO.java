package com.csa.bookstore.dao;

import com.csa.bookstore.entity.Author;
import com.csa.bookstore.exception.AuthorNotFoundException;
import java.util.ArrayList;
import java.util.List;
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

    public AuthorDAO() {
    }
    
    public Author addAuthor(Author author){
        int id = idCounter.incrementAndGet();
        author.setId(id);
        authors.put(id, author);
        return author;
    }
    
    public List<Author> getAllAuthors(){
        return new ArrayList<>(authors.values());
    }
    
    public Author getAuthorById(int id){
        Author author = authors.get(id);
        if(author == null) {
            throw new AuthorNotFoundException("Author "+id+" not found");
        }
        return author;
    }
    
    public Author updateAuthor(int id, Author updatedAuthor){
        if(!authors.containsKey(id)){
            throw new AuthorNotFoundException("Cannot update. Author with ID "+id+" not found");
        }
        updatedAuthor.setId(id);
        authors.put(id, updatedAuthor);
        return updatedAuthor;
    }
    
    public void deleteBook(int id){
        if(!authors.containsKey(id)){
            throw new AuthorNotFoundException("Cannot delete. Author with ID "+id+" not found");
        }
        authors.remove(id);
    }
    

}
