package org.example.Repository;

import org.example.Helpers.DbFunctions;
import org.example.Model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StatusRepository {

    DbFunctions database = new DbFunctions();

    Connection connection = database.connect_to_db();

    Scanner scanner;



    public int StatusExists(String Label){
        String checkCollectionQuery = "SELECT COUNT(*) FROM status WHERE label = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(checkCollectionQuery)) {
            pstmt.setString(1, Label);

            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            return resultSet.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean addStatus(String Label) throws SQLException {

            // Insert the new status label into the database
            String insertStatusQuery = "INSERT INTO status (label) VALUES (?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertStatusQuery)) {
                pstmt.setString(1, Label);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    return true;
                } else {
                    return false;
                }
            }
    }
    public Status getStatusById(long statusId) {
        String getStatusQuery = "SELECT * FROM Status WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(getStatusQuery)) {
            pstmt.setLong(1, statusId);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                // Retrieve status information from the ResultSet
                String label = resultSet.getString("label");

                // Create a Status object and return it
                return new Status(label);
            } else {
                return null; // Status with the specified ID not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getStatusIdByLabel(String label) throws SQLException {
        String getStatusIdQuery = "SELECT id FROM Status WHERE label = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(getStatusIdQuery)) {
            pstmt.setString(1, label);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            } else {
                throw new SQLException("Status not found for label: " + label);
            }
        }
    }
}
