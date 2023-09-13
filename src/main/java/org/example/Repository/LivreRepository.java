package org.example.Repository;

import org.example.Helpers.DbFunctions;
import org.example.Model.*;
import org.example.Model.Collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public Livre findNumInventaire(String numInventaire) {
        String query = "SELECT * FROM Livre WHERE numeroinventair = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, numInventaire);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                // Retrieve the data from the ResultSet and create a Livre object
                long id = resultSet.getLong("id");
                String numeroInventaire = resultSet.getString("numeroinventair");

                long collectionId = resultSet.getLong("collection_id");
                long statusId = resultSet.getLong("status_id");

                // Create a Book object and return it
                Collection collection = collectionRepository.getCollectionById(collectionId);
                Status status = statusRepository.getStatusById(statusId);
                return new Livre(id, numeroInventaire,collection,status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return null if no matching Livre is found
        return null;
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
        System.out.println(numeroLivre);
        try {
            // Get status_id based on status label
            System.out.print("Entrez le statut du livre: ");
            String statusLabel = scanner.next();
            status = new Status(statusLabel);

            while (statusRepository.StatusExists(statusLabel) == 0) {
                System.out.println("Ce statut " + statusLabel + " n'existe pas. Veuillez réessayer!\n");
                System.out.print("Entrez le statut du livre: ");
                statusLabel = scanner.next();
                status = new Status(statusLabel);
            }

            long statusId = statusRepository.getStatusIdByLabel(statusLabel);

            // Step 1: Get the ISBN
            System.out.print("Entrez l'ISBN du livre: ");
            String isbn = scanner.next();
            Collection collectionA = collectionRepository.getCollectionByIsbn(isbn);

            if (collectionA == null) {
                // Step 2: If collection with ISBN does not exist, create a new collection
                System.out.println("La collection avec l'ISBN " + isbn + " n'existe pas.");

                // Prompt the user for title and author of the new collection
                System.out.print("Entrez le titre de la nouvelle collection: ");
                String newCollectionTitle = scanner.next();
                System.out.print("Entrez l'auteur de la nouvelle collection: ");
                String newCollectionAuthor = scanner.next();

                // Create the new collection
                Collection newCollection = new Collection(newCollectionTitle, isbn, newCollectionAuthor);
                collectionRepository.AjouterCollection(newCollection);

                String query = "SELECT *  FROM collection WHERE isbn = ? ";
                try {
                    PreparedStatement prst = connection.prepareStatement(query);
                    prst.setString(1,newCollection.getIsbn());
                    ResultSet resultSet = prst.executeQuery();
                    while (resultSet.next()){
                        newCollection.setId(resultSet.getLong("id"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                // Set the created collection as the current collection
                collectionA = newCollection;
            }

            // Step 3: Insert the book into the database with the selected collection
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
            System.out.println("Entrée invalide. Veuillez entrer des données valides.");
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
                "INNER JOIN status ON livre.status_id = status.id WHERE status.label = 'Disponible' ";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {
            System.out.println("+--------------+------------------+------------------+------------------+------------------+------------------+");
            System.out.println("| Book ID      | Book ISBN        | Inventory Number | Title            | Author           | Status           |");
            System.out.println("+--------------+------------------+------------------+------------------+------------------+------------------+");

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String numLivre = resultSet.getString("numeroinventair");
                String isbn = resultSet.getString("collection_isbn");
                String title = resultSet.getString("collection_title");
                String author = resultSet.getString("collection_author");
                String status = resultSet.getString("status_label");

                // Print book information

                System.out.printf("| %-12d | %-16s | %-16s | %-16s | %-16s | %-16s |\n", id, isbn, numLivre, title, author, status);

                System.out.println("+--------------+------------------+------------------+------------------+------------------+------------------+");
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
/*
    public List<Livre> getBycollection(long collectionId){
       String Query = "SELECT * FROM livre JOIN collection ON livre.collection_id = collection.id WHERE collection_id = ?";

       List<Livre> livres = new ArrayList<>();

       try( PreparedStatement st = connection.prepareStatement(Query)){
           st.setLong(1,collectionId);

           ResultSet resultSet = st.executeQuery();

           if(resultSet.next()){
            Livre livre = new Livre();
            livre.mapData(resultSet);
            livres.add(livre);
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
        return livres;
    }*/

    public boolean DeleteLivre(String NumLivre){
        String DeleteBookQuery = "DELETE FROM Livre WHERE numeroinventair = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(DeleteBookQuery)) {
            pstmt.setString(1, NumLivre);
            int rowsDeleted = pstmt.executeUpdate();

            return rowsDeleted > 0; // Returns true if at least one row was deleted, indicating success

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public Livre findById(Long id)throws SQLException{
        String IdLivreQuery="Select * from Livre l Join collection c on l.collection_id=c.id where id=?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(IdLivreQuery)){
            preparedStatement.setLong(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                Livre livre=new Livre();
                livre.setId(resultSet.getLong("id"));
                livre.mapData(resultSet);
                return livre;
            }
        }
        return null;
    }


    public void displayBorrowedBooksInfo() {
        try {
            String query = "SELECT livreemprunt.*, livre.*, emprunteur.*, emprunt.*, collection.title AS book_title FROM livreemprunt INNER JOIN livre ON livreemprunt.livre_id = livre.id INNER JOIN emprunt ON livreemprunt.emprunt_id = emprunt.id INNER JOIN emprunteur ON emprunt.emprunteur_id = emprunteur.id INNER JOIN collection ON livre.collection_id = collection.id WHERE livre.status_id = 2";

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();
                List<String> borrowedBooksInfo = new ArrayList<>();

                System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.println("| Borrowed Books Information                                                                                                                 |");
                System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.println("| Book Title             | Borrower Name   | Start Date | End Date   | Returned       | Emprunteur Email | Emprunteur Phone |");
                System.out.println("+------------------------|-----------------|------------|------------|----------------|------------------|-------------------|");

                while (resultSet.next()) {
                    String bookTitle = resultSet.getString("book_title");
                    String borrowerName = resultSet.getString("fullname");
                    Date startDate = resultSet.getDate("startdate");
                    Date endDate = resultSet.getDate("enddate");
                    boolean isReturned = resultSet.getBoolean("returne");;
                    String emprunteurEmail = resultSet.getString("email");
                    String emprunteurPhone = resultSet.getString("phone");

                    // Format the dates
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedStartDate = dateFormat.format(startDate);
                    String formattedEndDate = dateFormat.format(endDate);

                    // Format the returned status
                    String returnedStatus = isReturned ? "Yes" : "No";

                    // Print row in tabular format
                    System.out.printf("| %-22s | %-15s | %-10s | %-10s | %-14s | %-16s | %-15s |\n",
                            bookTitle, borrowerName, formattedStartDate, formattedEndDate, returnedStatus,emprunteurEmail, emprunteurPhone);
                }

                System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayBookStatistics() {
        try {
            String query = "SELECT (SELECT COUNT(*) FROM livre WHERE status_id = (SELECT id FROM status WHERE label = 'Perdu')) AS perdus," +
                    "(SELECT COUNT(*) FROM livre WHERE status_id = (SELECT id FROM status WHERE label = 'Emprunte')) AS empruntes," +
                    "(SELECT COUNT(*) FROM livre WHERE status_id = (SELECT id FROM status WHERE label = 'Disponible')) AS disponibles";

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    int livresPerdus = resultSet.getInt("perdus");
                    int livresEmpruntes = resultSet.getInt("empruntes");
                    int livresDisponibles = resultSet.getInt("disponibles");

                    System.out.println("Statistiques des livres :");
                    System.out.println("+-------------------+-------------------+-------------------+");
                    System.out.println("| Livres Perdus     | Livres Empruntés  | Livres Disponibles|");
                    System.out.println("+-------------------+-------------------+-------------------+");
                    System.out.printf("| %-17d | %-17d | %-17d |\n", livresPerdus, livresEmpruntes, livresDisponibles);
                    System.out.println("+-------------------+-------------------+-------------------+");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 public int CountLivres(){

     String countLivreQuery = "SELECT COUNT(*) FROM livre";

     try (PreparedStatement pstmt = connection.prepareStatement(countLivreQuery)) {
         ResultSet resultSet = pstmt.executeQuery();

         if (resultSet.next()) {
             int bookCount = resultSet.getInt(1);
             return bookCount;
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }

     return -1;

 }

 public int CountBorrowedBooks(){
        String CountBorrowed = "SELECT COUNT(*) FROM livreemprunt";
     try (PreparedStatement pstmt = connection.prepareStatement(CountBorrowed)) {
         ResultSet resultSet = pstmt.executeQuery();

         if (resultSet.next()) {
             int bookBorrowed = resultSet.getInt(1);
             return bookBorrowed;
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }

     return -1;
 }



}



