package org.example.Repository;

import org.example.Helpers.DbFunctions;
import org.example.Model.Collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CollectionRepository {
    DbFunctions database = new DbFunctions();

    Connection connection = database.connect_to_db();

    private Scanner scanner;




    public boolean AjouterCollection(Collection collection){
        String addCollectionQuery = "INSERT INTO Collection (isbn, title, auteur) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(addCollectionQuery)) {

            pstmt.setString(1, collection.getIsbn());
            pstmt.setString(2, collection.getTitre());
            pstmt.setString(3, collection.getAuteur());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean addCollection() {
        scanner = new Scanner(System.in);
        System.out.print("Enter ISBN: ");
        String isbn = scanner.next();
        System.out.print("Enter Title: ");
        String title = scanner.next();
        System.out.print("Enter Author: ");
        String author = scanner.next();
        System.out.print("Enter Total Number of Books: ");
        int totalBooks = scanner.nextInt();
        System.out.println("Provided ISBN: " + isbn);

        String insertCollectionQuery = "INSERT INTO Collection (isbn, title, auteur, totale) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertCollectionQuery)) {
            pstmt.setString(1, isbn);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, totalBooks);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }



    public Collection getCollectionById(long collectionId) {
        String getCollectionQuery = "SELECT * FROM Collection WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(getCollectionQuery)) {
            pstmt.setLong(1, collectionId);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                // Retrieve collection information from the ResultSet
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("auteur");
                int totalBooks = resultSet.getInt("totale");

                // Create a Collection object and return it
                return new Collection(collectionId, title, totalBooks, author, isbn);
            } else {
                return null; // Collection with the specified ID not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection getCollectionByIsbn(String isbn) throws SQLException {
        String getCollectionQuery = "SELECT id, title, auteur, totale FROM Collection WHERE isbn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(getCollectionQuery)) {
            pstmt.setString(1, isbn);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                // Retrieve the collection information from the ResultSet
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("auteur");
                int totalBooks = resultSet.getInt("totale");

                // Create a Collection object and return it
                return new Collection(id, title, totalBooks, author, isbn);
            } else {
                return null;
            }
        }
    }
    public int collectionExists(String isbn) {
        String checkCollectionQuery = "SELECT COUNT(*) FROM collection WHERE isbn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(checkCollectionQuery)) {
            pstmt.setString(1, isbn);

            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            return resultSet.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean updateCollection(String isbn) {

            scanner = new Scanner(System.in);
            System.out.println("Provide updated information for the collection:");
            System.out.print("Enter new title: ");
            String newTitle = scanner.next();
            System.out.print("Enter new author: ");
            String newAuthor = scanner.next();
            System.out.print("Enter new total number of books: ");
            int newTotalBooks = scanner.nextInt();

            // Update the collection using SQL UPDATE statement
            String updateCollectionQuery = "UPDATE collection SET title = ?, auteur = ?, totale = ? WHERE isbn = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(updateCollectionQuery)) {
                pstmt.setString(1, newTitle);
                pstmt.setString(2, newAuthor);
                pstmt.setInt(3, newTotalBooks);
                pstmt.setString(4, isbn);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;

            }

    }

    public boolean DeleteCollection(long collectionId){
       String  Query = "DELETE FROM collection WHERE id = ? ";

       try(PreparedStatement pstmt = connection.prepareStatement(Query)){
           pstmt.setLong(1,collectionId);

           int rowsDeleted = pstmt.executeUpdate();

           return   rowsDeleted > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
    }

}
