package org.example.Service;

import org.example.Model.Emprunt;
import org.example.Model.Emprunteur;
import org.example.Model.Livre;
import org.example.Repository.EmpruntRepository;
import org.example.Repository.EmprunteurRepository;
import org.example.Repository.LivreRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EmpruntService {

    EmpruntRepository empruntRepository = new EmpruntRepository();
    LivreRepository livreRepository = new LivreRepository();

    EmprunteurRepository emprunteurRepository = new EmprunteurRepository();

    public String addEmprunt() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Retrieve the Emprunteur based on membership
        System.out.print("Enter the membership of the Emprunteur: ");
        String membership = scanner.next();
        Emprunteur emprunteur = emprunteurRepository.findByMembreShip(membership);

        if (emprunteur == null) {
            return "Emprunteur with membership " + membership + " does not exist.";
        }

        // Step 2: Retrieve the Livre objects based on numInventaire
        int numberOfLivres = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter the number of Livres to borrow: ");
                numberOfLivres = scanner.nextInt();
                validInput = true; // Input is valid, exit the loop
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }

        List<Long> livreIds = new ArrayList<>();

        for (int i = 0; i < numberOfLivres; i++) {
            System.out.print("Enter the numInventaire of Livre " + (i + 1) + ": ");
            String numInventaire = scanner.next();
            Livre livre = livreRepository.findNumInventaire(numInventaire);

            if (livre == null) {
                return "Livre with numInventaire " + numInventaire + " does not exist.";
            }

            livreIds.add(livre.getId());
        }

        // Step 3: Create an Emprunt object
        Emprunt emprunt = new Emprunt();
        emprunt.setEmprunteur(emprunteur);

        // Step 4: Set start date to the current date
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date sqlStartDate = new java.sql.Date(currentDate.getTime());
        emprunt.setDateDebut(sqlStartDate);

        // Step 5: Ask the user to enter the end date
        System.out.print("Enter the end date (yyyy-MM-dd): ");
        String endDateString = scanner.next();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false); // Set lenient to false to enforce strict date parsing
            Date endDate = dateFormat.parse(endDateString);
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
            emprunt.setDateRetour(sqlEndDate);
        } catch (ParseException e) {
            return "Invalid date format. Please enter the end date in yyyy-MM-dd format.";
        }

        // Step 6: Set the returne column to false by default
        emprunt.setRetourLivre(false);

        // Step 7: Save the Emprunt and the relationships with Livre
        boolean result = empruntRepository.saveEmprunt(emprunt, livreIds);

        if (result) {
            return "Emprunt added successfully.";
        } else {
            return "Failed to add Emprunt.";
        }
    }



    public List<Emprunt> findAll()throws  SQLException{
        List<Emprunt> emprunts=empruntRepository.findByAll();
        if(emprunts.size()==0){
            return null;
        }
        return emprunts;
    }

    public Emprunt findById(Long id)throws SQLException{
        Emprunt emprunt=empruntRepository.findById(id);
        if(emprunt!=null){
            return emprunt;
        }
        return null;
    }


    public String returne(String numeroInventair)throws  SQLException{
        Livre livre=livreRepository.findNumInventaire(numeroInventair);
        if(livre!=null){
            Emprunt emprunt=empruntRepository.findEmpruntLivre(livre);
            if(emprunt!=null){
                empruntRepository.delete(livre.getId());
                if(empruntRepository.checkCount(emprunt.getId())==0){
                    empruntRepository.update(emprunt.getId());
                    return "the emprunt has been updated successfully";
                }
                return "book with numeroInventair :"+numeroInventair+"has been returned successfully";
            }
            return "emprunt of this book doesn't exist";
        }
        return "this book doesn't exist";
    }




}

