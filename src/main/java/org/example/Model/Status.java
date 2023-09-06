package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Status {

    private long Id;
    private String Label;

    public Status(String label) {
        Label = label;
    }

    public Long getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }


    public boolean addStatus(Connection conn) {
        // Check if the status label already exists in the database
        if (statusExists(conn)) {
            System.out.println("Status with label " + Label + " already exists.");
            return false;
        }

        String insertStatusQuery = "INSERT INTO status (label) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertStatusQuery)) {
            pstmt.setString(1, Label);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add a method to check if a status label already exists in the database
    private boolean statusExists(Connection conn) {
        String checkStatusQuery = "SELECT COUNT(*) FROM status WHERE label = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkStatusQuery)) {
            pstmt.setString(1, Label);

            // Execute the query and check if any rows exist
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteStatus(Status status){}

    public void updateStatus(Status status){}

    public void showStatus(Status status){}

}
