package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Collection {
    private long Id;
    private String Titre;
    private int NbrLivre;
    private String Isbn;
    private String Auteur;

    private ArrayList<Livre> livres ;

    public Collection(String titre, int nbrLivre, String isbn, String auteur) {
        Titre = titre;
        NbrLivre = nbrLivre;
        Isbn = isbn;
        Auteur = auteur;
    }

    public Collection(){}
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public int getNbrLivre() {
        return NbrLivre;
    }

    public void setNbrLivre(int nbrLivre) {
        NbrLivre = nbrLivre;
    }

    public String getIsbn() {
        return Isbn;
    }

    public void setIsbn(String isbn) {
        Isbn = isbn;
    }

    public String getAuteur() {
        return Auteur;
    }

    public void setAuteur(String auteur) {
        Auteur = auteur;
    }

    public boolean addCollection(Connection conn, Scanner scanner) {
        // Get user input for collection details
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
            try (PreparedStatement pstmt = conn.prepareStatement(insertCollectionQuery)) {
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




}
