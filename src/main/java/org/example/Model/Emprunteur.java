package org.example.Model;

public class Emprunteur {

    private long Id;
    private String Nom;
    private String Email;
    private String Telephone;
    private String Membership;

    public Emprunteur(String nom, String email, String telephone, String membership) {
        Nom = nom;
        Email = email;
        Telephone = telephone;
        Membership = membership;
    }

    public long getId() {
        return Id;
    }

    public void setId(int id) {
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
}
