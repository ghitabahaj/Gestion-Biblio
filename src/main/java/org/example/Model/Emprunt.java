package org.example.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Emprunt {

    private long Id;
    private LocalDate DateDebut;
    private LocalDate DateRetour;
    private Boolean RetourLivre;
    private ArrayList<Livre> livres;
    private Emprunteur emprunteur;

    public Emprunt(LocalDate dateDebut, LocalDate dateRetour, Boolean retourLivre, ArrayList<Livre> livres, Emprunteur emprunteur) {
        DateDebut = dateDebut;
        DateRetour = dateRetour;
        RetourLivre = retourLivre;
        this.livres = livres;
        this.emprunteur = emprunteur;
    }

    public long getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public LocalDate getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        DateDebut = dateDebut;
    }

    public LocalDate getDateRetour() {
        return DateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        DateRetour = dateRetour;
    }

    public Boolean getRetourLivre() {
        return RetourLivre;
    }

    public void setRetourLivre(Boolean retourLivre) {
        RetourLivre = retourLivre;
    }

    public ArrayList<Livre> getLivres() {
        return livres;
    }

    public void setLivres(ArrayList<Livre> livres) {
        this.livres = livres;
    }

    public Emprunteur getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Emprunteur emprunteur) {
        this.emprunteur = emprunteur;
    }
}
