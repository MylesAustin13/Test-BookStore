package org.example.Users;

import java.sql.SQLException;

public interface UserDao {

    void addUser(User user) throws SQLException;

    User getUserByUsername(String username) throws SQLException;



}
