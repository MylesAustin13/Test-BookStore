package com.company;

import javax.sound.midi.Soundbank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao{

    Connection connection;

  public UserDaoImpl(){
      this.connection = ConnectionFactory.getConnection();
  }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "INSERT into user (username, password) values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2,user.getPassword());

        int count = preparedStatement.executeUpdate();

        if (count > 0) {
            System.out.println("User added");
        }
        else {
            System.out.println("Ooops!!");
        }
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        //User userA = new User();
        String sql = "SELECT * FROM user WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,username);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            int id = resultSet.getInt(1);
            String userName = resultSet.getString(2);
            String password = resultSet.getString(3);

            User userNew = new User(id,username,password);
            return userNew;
        }
        else {
            System.out.println("user does not exist");

            return null;
        }
    }
}
