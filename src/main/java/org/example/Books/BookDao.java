package org.example.Books;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {

    void addBook(Book book) throws SQLException;

    List<Book> getBooksByCategory (String category) throws SQLException;
    List<Book> getAllBooks() throws SQLException;


}
