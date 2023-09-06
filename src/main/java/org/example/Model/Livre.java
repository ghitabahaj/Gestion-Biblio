package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Livre {
    private long Id;
    private String numLivre;
    private Collection collection;

    private Status status;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.
                status = status;
    }
    public Livre(String numLivre, Collection collection, Status status) {
        this.numLivre = numLivre;
        this.collection = collection;
        this.status = status;
    }

    public long getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNumLivre() {
        return numLivre;
    }

    public void setNumLivre(String numLivre) {
        this.numLivre = numLivre;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    // Add a method to add a book to the database
    public boolean addBook(Connection conn) {


        String insertBookQuery = "INSERT INTO book (numeroinventair, status_id, collection_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertBookQuery)) {
            pstmt.setString(1, numLivre);
            pstmt.setLong(2, status.getId());
            pstmt.setLong(3, collection.getId()); // Use the ID of the associated collection

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
