package com.csa.bookstore.entity;


/**
 *
 * @author Soshan Wijayarathne
 */
public class Book {
    
    private String title;
    private int authorId;
    private String ISBN;
    private int publicationYear;
    private double price;
    private int stockQuantity;
    
    private transient String authorName;

    public Book() {
    }

    public Book(String title, int authorId, String ISBN, int publicationYear, double price, int stockQuantity) {
        this.title = title;
        this.authorId = authorId;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Book{" + "title=" + title + ", authorId=" + authorId + ", ISBN=" + ISBN + ", publicationYear=" + publicationYear + ", price=" + price + ", stockQuantity=" + stockQuantity + ", authorName=" + authorName + '}';
    }
}
