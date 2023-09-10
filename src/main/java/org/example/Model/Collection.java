package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public Collection(Long id ,String titre, int nbrLivre, String isbn, String auteur) {
        Id = id;
        Titre = titre;
        NbrLivre = nbrLivre;
        Isbn = isbn;
        Auteur = auteur;
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


