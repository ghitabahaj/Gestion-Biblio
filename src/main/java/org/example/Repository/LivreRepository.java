package org.example.Repository;

import org.example.Helpers.DbFunctions;
import org.example.Model.Collection;
import org.example.Model.Livre;
import org.example.Model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LivreRepository {

    private Scanner scanner;

    private Status status = new Status();

    private Collection collection = new Collection();

    DbFunctions database = new DbFunctions();


    Connection connection = database.connect_to_db();

    CollectionRepository collectionRepository = new CollectionRepository();

    StatusRepository statusRepository = new StatusRepository();



    public boolean checkIfBookExistsByNUM(String numero) {
        String checkBookQuery = "SELECT COUNT(*) FROM livre WHERE numeroinventair = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(checkBookQuery)) {
            pstmt.setString(1, numero);

            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Livre findBookByTitle(String title) {
        String findBookQuery = "SELECT * FROM livre INNER JOIN collection ON livre.collection_id = collection.id WHERE collection.title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(findBookQuery)) {
            pstmt.setString(1, title);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                // Retrieve book information from the ResultSet
                long id = resultSet.getLong("id");
                String numLivre = resultSet.getString("numeroinventair");
                long collectionId = resultSet.getLong("collection_id");
                long statusId = resultSet.getLong("status_id");

                // Create a Book object and return it
                Collection collection = collectionRepository.getCollectionById(collectionId);
                Status status = statusRepository.getStatusById(statusId);
                return new Livre(id, numLivre, collection, status);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void displayBookByTitle(String title) {

        Livre book = findBookByTitle(title);

        if (book != null) {
            System.out.println("Book Found:");
            System.out.println("ID: " + book.getId());
            System.out.println("Title: " + book.getCollection().getTitre());
            System.out.println("ISBN: " + book.getCollection().getIsbn());
            System.out.println("Author: " + book.getCollection().getAuteur());
            System.out.println("Status: " + book.getStatus().getLabel());
            System.out.println("Inventory Number: " + book.getNumLivre());
        } else {
            System.out.println("Book with title \"" + title + "\" not found.");
        }
    }


    public boolean addBook(String numeroLivre) {
        scanner = new Scanner(System.in);
        try {
            // Get status_id based on status label
            System.out.print("Entrez le status de livre: ");
            String statusLabel = scanner.next();
            status = new Status(statusLabel);

            while (statusRepository.StatusExists(statusLabel) == 0) {
                System.out.println("Ce Status " + statusLabel + " n'existe pas . Veuillez réssayer!\n");
                System.out.print("Entrez le status de livre: ");
                statusLabel = scanner.next();
                status = new Status(statusLabel);
            }

            long statusId = statusRepository.getStatusIdByLabel(statusLabel);


            System.out.print("Entrez la collection de livre (ISBN): ");
            String isbn = scanner.next();
            collection = new Collection(isbn);
            Collection collectionA = collectionRepository.getCollectionByIsbn(isbn);

            String insertBookQuery = "INSERT INTO livre (numeroinventair, status_id, collection_id) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertBookQuery)) {
                pstmt.setString(1, numeroLivre);
                pstmt.setLong(2, statusId);
                pstmt.setLong(3, collectionA.getId());

                int rowsInserted = pstmt.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid data.");
            scanner.next();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void displayAllBooks() {
        String query = "SELECT livre.*, collection.title AS collection_title, " +
                "collection.auteur AS collection_author, collection.isbn AS collection_isbn , status.label AS status_label " +
                "FROM livre " +
                "INNER JOIN collection ON livre.collection_id = collection.id " +
                "INNER JOIN status ON livre.status_id = status.id WHERE status.label = 'disponible' OR status.label = 'emprunte'";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String numLivre = resultSet.getString("numeroinventair");
                String isbn = resultSet.getString("collection_isbn");
                String title = resultSet.getString("collection_title");
                String author = resultSet.getString("collection_author");
                String status = resultSet.getString("status_label");

                // Print book information
                System.out.println("Book ID: " + id);
                System.out.println("Book ISBN: " + isbn);
                System.out.println("Inventory Number: " + numLivre);
                System.out.println("Title: " + title);
                System.out.println("Author: " + author);
                System.out.println("Status: " + status);
                System.out.println("------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Long CheckIfBorrowed(String NumInv) {
        String CheckIfBorrowedQuery = "SELECT id FROM livre JOIN livreemprunt ON livre.id = livreemprunt.livre_id WHERE numeroinventair = ?";

        try (PreparedStatement stmt = connection.prepareStatement(CheckIfBorrowedQuery)) {
            stmt.setString(1, NumInv);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("id");
            } else {
                return null; // Return null to indicate not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }



    public boolean DeleteBook(String numLivre) {
        if (CheckIfBorrowed(numLivre)!= null) {
            System.out.println("This book is already borrowed and cannot be deleted.");
            return false;
        }else {

            String DeleteBookQuery = "DELETE FROM Livre WHERE numeroinventair = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(DeleteBookQuery)) {
                pstmt.setString(1, numLivre);
                int rowsDeleted = pstmt.executeUpdate();
                return rowsDeleted > 0; // Returns true if at least one row was deleted, indicating success
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}



