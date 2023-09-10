package org.example.Model;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Emprunteur {

    private long Id;
    private String Nom;
    private String Email;
    private String Telephone;
    private String Membership;

    private List<Emprunt> empruntList;

    public Emprunteur(String nom, String email, String telephone, String membership) {
        Nom = nom;
        Email = email;
        Telephone = telephone;
        Membership = membership;
    }

    public Emprunteur(){}
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getMembership() {
        return Membership;
    }

    public void setMembership(String membership) {
        Membership = membership;
    }


    public List<Emprunt> getEmpruntList() {
        return empruntList;
    }

    public void setEmpruntList(List<Emprunt> empruntList) {
        this.empruntList = empruntList;
    }

    public Emprunteur mapData(ResultSet resultSet) throws SQLException {

        this.Membership = resultSet.getString("membreShip");
        this.Nom = resultSet.getString("fullName");
        this.Email = resultSet.getString("email");
        this.Telephone = resultSet.getString("phone");

        return this;
    }

}
