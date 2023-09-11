package org.example.Controller;

import org.example.Model.Collection;
import org.example.Model.Emprunt;
import org.example.Model.Emprunteur;
import org.example.Model.Livre;
import org.example.Service.EmpruntService;

import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmpruntController {


    EmpruntService empruntService = new EmpruntService();



    public void addEmprunt() throws SQLException {

        String message = empruntService.addEmprunt();

        System.out.println(message);
    }


    public void findAll(Emprunteur emprunteur) throws SQLException {
        List<Emprunt> emprunts = empruntService.findAll();

        for (Emprunt emprunt : emprunts) {
            System.out.println("Emprunt Date: " + emprunt.getDateDebut());
            System.out.println("Emprunt End Date: " + emprunt.getDateRetour());
            System.out.println("Emprunt State: " + (emprunt.getRetourLivre() ? "Returned" : "Not Returned"));
            System.out.println("Emprunteur Name: " + emprunt.getEmprunteur().getNom());
            System.out.println("Emprunteur Membership: " + emprunt.getEmprunteur().getMembership());
            System.out.println("Emprunteur Phone Number: " + emprunt.getEmprunteur().getTelephone());
            System.out.println("Emprunteur Email: " + emprunt.getEmprunteur().getEmail());

            List<Livre> livres = emprunt.getLivres();
            System.out.println("Borrowed Livres:");
            for (Livre livre : livres) {
                Collection collection = livre.getCollection();
                System.out.println("   Livre NumeroInventair: " + livre.getNumLivre());
                System.out.println("   Livre ISBN: " + collection.getIsbn());
                System.out.println("   Livre Title: " + collection.getTitre());
                System.out.println("   Livre Auteur: " + collection.getAuteur());
            }

            System.out.println();
        }
    }

}

