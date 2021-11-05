package com.company.Books;

import com.company.ConnectionFactory;
import com.company.User;
import jdk.jfr.Category;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao{

    Connection connection;

    public BookDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addBook(Books books) throws SQLException {
        String sql ="insert into books (tile, author, price, category, description, isbn) values (?,?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); //fill id field after adding
        preparedStatement.setString(1, books.getTitle());
        preparedStatement.setString(2,books.getAuthor());
        preparedStatement.setDouble(3,books.getPrice());
        preparedStatement.setString(4,books.getCategory());
        preparedStatement.setString(5,books.getDescription());
        preparedStatement.setInt(6,books.getIsbn());

        ResultSet resultSet = preparedStatement.getGeneratedKeys(); //watch this line


        int count = preparedStatement.executeUpdate();
        resultSet = preparedStatement.getGeneratedKeys();
        while(resultSet.next()){
           // books.setId(resultSet.getInt(1,books.getCategory());
        }
        if (count > 0)
            System.out.println("Book saved");
        else
            System.out.println("Attempt Failed");
    }

    @Override
    public List<Books> getBooksByCategory(String category) throws SQLException {
        List<Books> books1 = new ArrayList<>();
        String sql = "SELECT * FROM books Where LOWER (category) = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,category.toLowerCase());


        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            String cat = resultSet.getString(5);
            String desc = resultSet.getString(6);
            int isbn = resultSet.getInt(7);

// Books(int id, String title, String author, double price, String category, String description,
// from constructor in Book Class already declared

            Books bookNew = new Books(id, title, author, price, cat, desc, isbn);

            books1.add(bookNew);
        }

        return books1;
    }

    @Override
    public List<Books> getAllBooks() throws SQLException {

        String sql ="SELECT * FROM books";
        List<Books> bookAll = new ArrayList<>(); /// Create the list to be returned
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            String cat = resultSet.getString(5);
            String desc = resultSet.getString(6);
            int isbn = resultSet.getInt(7);

            Books book2 = new Books(id, title, author, price, cat, desc, isbn);
            bookAll.add(book2);

        }
        return bookAll;
    }

    @Override
    public Books getBookById(int bookid) throws SQLException {

        String sql = "SELECT * FROM books WHERE bookid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,bookid);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            String category = resultSet.getString(5);
            String desc = resultSet.getString(6);
            int isbn = resultSet.getInt(7);

            Books booksA = new Books(id, title,author, price, category, desc, isbn);
            return booksA;
        }
        else {
            System.out.println("No such book exists here");

            return null;
        }

    }
}
