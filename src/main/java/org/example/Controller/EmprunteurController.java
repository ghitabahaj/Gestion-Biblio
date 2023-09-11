package org.example.Controller;

import org.example.Model.Emprunteur;
import org.example.Service.EmprunteurService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmprunteurController {

    EmprunteurService emprunteurService = new EmprunteurService();

    public void save() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MemberShip number: ");
        String membership = scanner.next();
        System.out.print("Enter fullNumber: ");
        String fullName = scanner.next();
        System.out.print("Enter Email: ");
        String email = scanner.next();
        System.out.print("Enter Phone: ");
        String phone = scanner.next();
        Emprunteur emprunteur = new Emprunteur(fullName, email, phone, membership);
        emprunteurService.save(emprunteur);

    }

    public void findAll() throws SQLException {
        List<Emprunteur> emprunteurs = emprunteurService.findAll();

        System.out.println("-------------------------------------------------------------");
        System.out.printf("| %-15s | %-30s | %-15s | %-30s |\n", "Membership", "Fullname", "Phone", "Email");
        System.out.println("-------------------------------------------------------------");

        for (Emprunteur emprunteur : emprunteurs) {
            System.out.printf("| %-15s | %-30s | %-15s | %-30s |\n",
                    emprunteur.getMembership(),
                    emprunteur.getNom(),
                    emprunteur.getTelephone(),
                    emprunteur.getEmail()
            );
        }

        System.out.println("-------------------------------------------------------------");
    }

    public void update() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MemberShip number: ");
        String membership = scanner.next();
        if (emprunteurService.findByMemberShip(membership) != null) {
            System.out.print("Enter fullNumber: ");
            String fullName = scanner.next();
            System.out.print("Enter Email: ");
            String email = scanner.next();
            System.out.print("Enter Phone: ");
            String phone = scanner.next();
            Emprunteur emprunteur = new Emprunteur(fullName, email, phone,membership);
            System.out.println(emprunteurService.update(emprunteur));
        }

    }

    public String delete() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MemberShip number: ");
        String membership = scanner.next();
       if(emprunteurService.deleteByMembership(membership)){
           return " Emprunteur Deleted successufully";
       }else{
           return "An error occured try again";
       }
    }
}
