# Bookstore REST API

A Java-based RESTful API for managing an online bookstore system. This project provides endpoints for managing books, authors, customers, shopping carts, and orders.

## Project Overview

This Bookstore API is built using JAX-RS (Java API for RESTful Web Services) and provides a complete backend system for an online bookstore with the following features:

- Book management (add, update, delete, list)
- Author management (add, update, delete, list)
- Customer management (add, update, delete, list)
- Shopping cart functionality
- Order processing

## Technology Stack

- Java 8
- JAX-RS/Jersey 2.32 (REST API framework)
- Maven (dependency management)
- JSON for data serialization/deserialization
- Jakarta EE 8

## Project Structure

The project follows a standard layered architecture:

- **Entity Layer**: Data models representing domain objects
- **DAO Layer**: Data Access Objects for CRUD operations
- **Resource Layer**: REST endpoints exposing the API
- **Exception Layer**: Custom exceptions and exception mappers

### Key Components

#### Entities
- Book
- Author
- Customer
- Cart
- Order

#### Data Access Objects (DAOs)
- BookDAO
- AuthorDAO
- CustomerDAO
- CartDAO
- OrderDAO

#### REST Resources
- BookResource
- AuthorResource
- CustomerResource
- CartResource
- OrderResource

## API Endpoints

### Books

```
GET    /books         - Get all books
GET    /books/{id}    - Get a specific book by ISBN
POST   /books         - Add a new book
PUT    /books/{id}    - Update a book
DELETE /books/{id}    - Delete a book
```

### Authors

```
GET    /authors          - Get all authors
GET    /authors/{id}     - Get a specific author
POST   /authors          - Add a new author
PUT    /authors/{id}     - Update an author
DELETE /authors/{id}     - Delete an author
GET    /authors/{id}/books - Get all books by an author
```

### Customers

```
GET    /customers         - Get all customers
GET    /customers/{id}    - Get a specific customer
POST   /customers         - Add a new customer
PUT    /customers/{id}    - Update a customer
DELETE /customers/{id}    - Delete a customer
```

### Shopping Cart

```
GET    /customer/{customerId}/cart         - Get customer's cart
POST   /customer/{customerId}/cart/items   - Add item to cart
PUT    /customer/{customerId}/cart/item/{bookId} - Update item quantity
DELETE /customer/{customerId}/cart/items/{bookId} - Remove item from cart
```

### Orders

```
POST   /customers/{customerId}/orders         - Create a new order from cart
GET    /customers/{customerId}/orders         - Get all orders for a customer
GET    /customers/{customerId}/orders/{orderId} - Get a specific order
```

## Data Storage

This implementation uses in-memory storage (ConcurrentHashMap) for demonstration purposes. For production use, it should be replaced with a proper database integration.

## Exception Handling

The API implements comprehensive exception handling with custom exceptions:

- BookNotFoundException
- AuthorNotFoundException
- CustomerNotFoundException
- CartNotFoundException
- OrderNotFoundException
- InvalidInputException
- OutOfStockException

Each exception has a corresponding ExceptionMapper that transforms exceptions into appropriate HTTP responses with error codes and messages.

## How to Run

### Prerequisites

- Java JDK 8 or higher
- Maven 3.6 or higher

### Build and Run

1. Clone the repository
   ```
   git clone [repository-url]
   cd bookstore
   ```

2. Build the project
   ```
   mvn clean install
   ```

3. Deploy the WAR file to a servlet container (like Tomcat or Jetty)
   ```
   cp target/BookStore-1.0-SNAPSHOT.war [servlet-container-webapps-dir]
   ```

Or use Maven plugins to run directly:
   ```
   mvn jetty:run
   ```

The API will be available at:
```
http://localhost:8080/BookStore-1.0-SNAPSHOT/rest/
```

## Development

### Adding a New Entity

1. Create a new entity class in the `com.csa.bookstore.entity` package
2. Create a corresponding DAO in the `com.csa.bookstore.dao` package
3. Create custom exceptions as needed
4. Create exception mappers in `com.csa.bookstore.exception.mapper`
5. Create a resource class in `com.csa.bookstore.resource`

### Data Validation

Input validation is implemented in the resource layer. For example, customer data is validated to ensure valid email formats and minimum password lengths.

## Future Enhancements

- Database integration (JPA/Hibernate)
- Authentication and authorization
- Pagination for listing endpoints
- Advanced search functionality
- Swagger/OpenAPI documentation
- Unit and integration tests

## Contributors

- Soshan Wijayarathne - Initial work

