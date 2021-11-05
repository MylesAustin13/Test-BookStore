package com.company.Books;
import com.company.Books.Books;
import com.company.User;

import java.awt.print.Book;
import java.sql.SQLException;
import java.util.List;

public interface BookDao {
    void addBook(Books books) throws SQLException;

    List<Books> getBooksByCategory(String category) throws SQLException;  // get list of books

    List<Books> getAllBooks() throws SQLException;

    Books getBookById(int bookid) throws SQLException;


}
