package org.example.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Date;

public class Emprunt {

    private long Id;
    private Date DateDebut;
    private Date DateRetour;
    private Boolean RetourLivre;
    private List<Livre> livres;
    private Emprunteur emprunteur;



    public Emprunt(Date dateDebut, Date dateRetour, Boolean retourLivre, List<Livre> livres, Emprunteur emprunteur) {
        DateDebut = dateDebut;
        DateRetour = dateRetour;
        RetourLivre = retourLivre;
        this.livres = livres;
        this.emprunteur = emprunteur;
    }

    public Emprunt(){}

    public Emprunt(Date date, Date parse, boolean b, Emprunteur emprunteur) {
        DateDebut = date;
        DateRetour = parse ;
        RetourLivre = b;
        this.emprunteur = emprunteur;
    }

    public long getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        DateDebut = dateDebut;
    }

    public Date getDateRetour() {
        return DateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        DateRetour = dateRetour;
    }

    public Boolean getRetourLivre() {
        return RetourLivre;
    }

    public void setRetourLivre(Boolean retourLivre) {
        RetourLivre = retourLivre;
    }

    public List<Livre> getLivres() {
        return livres;
    }

    public void setLivres(List<Livre> livres) {
        this.livres = livres;
    }

    public Emprunteur getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Emprunteur emprunteur) {
        this.emprunteur = emprunteur;
    }

    public Emprunt mapData(ResultSet resultSet) throws SQLException {

        this.Id = resultSet.getLong("id");
        this.DateDebut = resultSet.getDate("startDate");
        this.DateRetour = resultSet.getDate("endDate");
        this.RetourLivre = resultSet.getBoolean("returne");
        return this;
    }
}
