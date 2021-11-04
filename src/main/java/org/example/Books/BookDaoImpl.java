package org.example.Books;

import org.example.ConnectionFactory;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookDaoImpl implements BookDao{

    Connection connection;

    public BookDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addBook(Book book) throws SQLException {
        String sql = "insert into books (title, author, price, category, description, isbn) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement prepped = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        prepped.setString(1, book.getTitle());
        prepped.setString(2, book.getAuthor());
        prepped.setDouble(3, book.getPrice());
        prepped.setString(4, book.getCategory());
        prepped.setString(5, book.getDescription());
        prepped.setInt(6, book.getIsbn());

        int rows = prepped.executeUpdate();
        ResultSet resultSet = prepped.getGeneratedKeys(); //Get the id
        while(resultSet.next()){
            book.setId(resultSet.getInt(1));
        }

        if(rows > 0){
            System.out.println("Book added!");
        }
        else{
            System.out.println("Problem adding this book.");
        }

    }

    @Override
    public List<Book> getBooksByCategory(String category) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "select * from books where category = ?";

        PreparedStatement prepped = connection.prepareStatement(sql);
        prepped.setString(1, category.toLowerCase());

        ResultSet resultSet = prepped.executeQuery();

        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            String cat = resultSet.getString(5);
            String desc = resultSet.getString(6);
            int isbn = resultSet.getInt(7);

            Book book = new Book(id, title, author, price, cat, desc, isbn);
            books.add(book);
        }

        return books;


    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "select * from books";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            String cat = resultSet.getString(5);
            String desc = resultSet.getString(6);
            int isbn = resultSet.getInt(7);

            Book book = new Book(id, title, author, price, cat, desc, isbn);
            books.add(book);
        }

        return books;
    }
}
