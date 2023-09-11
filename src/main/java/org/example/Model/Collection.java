package org.example.Model;

import org.example.Helpers.DbFunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Collection {
    DbFunctions database = new DbFunctions();
    Connection connection = database.connect_to_db();
    private long Id;
    private String Titre;
    private int NbrLivre;
    private String Isbn;
    private String Auteur;

    private ArrayList<Livre> livres ;

    public Collection(Long id ,String titre, int nbrLivre, String isbn, String auteur) {
        Id = id;
        Titre = titre;
        NbrLivre = nbrLivre;
        Isbn = isbn;
        Auteur = auteur;
    }
    public Collection(String titre, String isbn, String auteur) {

        Titre = titre;
        Isbn = isbn;
        Auteur = auteur;
    }
    public void get(){
        String query = "SELECT *  FROM collection WHERE isbn = ? ";
        try {
            PreparedStatement prst = connection.prepareStatement(query);
            prst.setString(1,this.Isbn);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                this.Id = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Collection(){}
    public Collection(String isbn){
        Isbn = isbn;
    }
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


    public Collection mapData(ResultSet resultSet) throws SQLException {

        this.Auteur = resultSet.getString("auteur");
        this.Isbn = resultSet.getString("isbn");
        this.Titre = resultSet.getString("title");
        this.NbrLivre = resultSet.getInt("totale");

        return this;
    }

    }


