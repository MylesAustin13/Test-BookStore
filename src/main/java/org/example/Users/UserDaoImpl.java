package org.example.Users;

import org.example.Books.Book;
import org.example.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    Connection connection;

    public UserDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "insert into users (username, password) values (?, ?)";
        PreparedStatement prepped = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        prepped.setString(1, user.getUsername());
        prepped.setString(2, user.getPassword());

        int rows = prepped.executeUpdate();
        ResultSet resultSet = prepped.getGeneratedKeys(); //Get the id
        while(resultSet.next()){
            user.setId(resultSet.getInt(1));
        }

        if(rows > 0){
            System.out.println("Book added!");
        }
        else{
            System.out.println("Problem adding this book.");
        }
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        String sql = "select * from users where username = ?";

        PreparedStatement prepped = connection.prepareStatement(sql);
        prepped.setString(1,username);

        ResultSet resultSet = prepped.executeQuery();
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);


            User user = new User(id, name, password);
            return user;
        }
        else{
            System.out.println("No user found!");
            return null;
        }

    }
}
