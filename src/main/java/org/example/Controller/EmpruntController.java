package org.example.Controller;

import org.example.Model.Collection;
import org.example.Model.Emprunt;
import org.example.Model.Emprunteur;
import org.example.Model.Livre;
import org.example.Service.EmpruntService;
import org.example.Service.EmprunteurService;

import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmpruntController {


    EmpruntService empruntService = new EmpruntService();
    EmprunteurService emprunteurService = new EmprunteurService();

    EmprunteurController emprunteurController = new EmprunteurController();

    Scanner scanner = new Scanner(System.in);
    public void addEmprunt() throws SQLException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> data = new ArrayList<>();
        int exit = 1;
        String member = null;
        Emprunteur emprunteur = null;

        while (exit != 0 && exit!=2) {
            System.out.print("Enter Emprunteur memberShip : ");
            member = scanner.next();
            emprunteur = emprunteurService.findByMemberShip(member);

            if (emprunteur == null) {
                System.out.println("This emprunteur doesn't exist");
                System.out.println("Choose an option:");
                System.out.println("1. Re-enter the membership of the borrower");
                System.out.println("2. Create a new borrower");
                System.out.println("3. Exit");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        emprunteurController.save();
                        emprunteur = emprunteurService.findByMemberShip(member);
                        exit=2;
                        break;
                    case 3:
                        System.out.println("Exiting.");
                        exit = 0; // Set the exit flag to true
                        break;
                    default:
                        System.out.println("Invalid choice. Exiting.");
                        exit = 0; // Set the exit flag to true
                        break;
                }
            } else {
                exit = 2;
            }
        }
        if (exit == 0)  {
            return ;
        }
        System.out.print("Enter End date (YYYY-MM-DD): ");
        String EndDate = scanner.next();
        emprunteur.setMembership(member);

        System.out.print("Enter the number of livres empruntes by the emprunteur: ");
        Integer n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter the numero d'inventaire of " + (i + 1) + " livre: ");
            String id = scanner.next();
            data.add(id);
        }
        try {
            Emprunt emprunt = new Emprunt(new Date(),dateFormat.parse(EndDate),false,emprunteur);
            System.out.print(empruntService.addEmprunt(emprunt, data) + "\n");
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
            System.out.print("The save operation of emprunt failed.\n");
        }


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

            System.out.println("---------------------------------------");
        }
    }


    public void Returned() throws SQLException {



        System.out.println("Entrez le numero d'inventaire:");
        String numLivre = scanner.next();

         String message = empruntService.returne(numLivre);
         System.out.println(message);
    }

}

