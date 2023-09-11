package org.example.Controller;

import org.example.Service.LivreService;

import java.util.Scanner;

public class LivreController {

    LivreService livreService = new LivreService();
    Scanner scanner = new Scanner(System.in);
    public void AddLivre(){

        System.out.println("Veuillez entrer le numero de livre à ajouter:");
        String numeroLivre = scanner.next();
        System.out.println(livreService.AddBook(numeroLivre));
    }

    public void delete(){

        System.out.println("Entrez le numéro d'inventaire:");
        String numLivre = scanner.next();
        System.out.println(livreService.DeleteBook(numLivre));
    }

    public void DisplayByTitle(){
        System.out.println("Veuillez entrer le titre de livre que vous voulez rechercher?:");
        String Titre = scanner.next();
        livreService.DisplayBook(Titre);
    }

    public void DisplayAllBooks(){
        String message = livreService.DisplayAllBooks();
        System.out.println(message);
    }

    public void DisplayBorrowedBooks(){
       livreService.DisplayBooksBorrowed();
    }


}
