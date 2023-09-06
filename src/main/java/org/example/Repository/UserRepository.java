package org.example.Repository;
import org.example.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public User findByUsername(String username) {
        String query = "SELECT id, username, password FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String foundUsername = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    return new User(id, foundUsername, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }
}
