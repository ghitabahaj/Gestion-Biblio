package org.example.Controller;

import org.example.Service.LivreService;

import java.util.Scanner;

public class LivreController {

    LivreService livreService = new LivreService();
    Scanner scanner = new Scanner(System.in);
    public void AddLivre(){

        System.out.println("Veuillez entrer le numero de livre à ajouter:");
        String numeroLivre = scanner.next();
        livreService.AddBook(numeroLivre);
    }

    public void delete(){

        System.out.println("Entrez le numéro d'inventaire:");
        String numLivre = scanner.next();
        livreService.DeleteBook(numLivre);
    }


}
