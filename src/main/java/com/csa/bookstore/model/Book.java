package com.csa.bookstore.model;


/**
 *
 * @author Soshan Wijayarathne
 */
public class Book {
    
    private String title;
    private String author;
    private String ISBN;
    private int publicationYear;
    private double price;
    private int stockQuantity;

    public Book() {
    }

    public Book(String title, String author, String ISBN, int publicationYear, double price, int stockQuantity) {
        this.title = title;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "Book{" + "title=" + title + ", author=" + author + ", ISBN=" + ISBN + ", publicationYear=" + publicationYear + ", price=" + price + ", stockQuantity=" + stockQuantity + '}';
    }
    
}
