package org.example.Controller;

import org.example.Service.CollectionService;

import java.sql.SQLException;
import java.util.Scanner;

public class CollectionController {

    CollectionService collectionService = new CollectionService();
    Scanner scanner = new Scanner(System.in);

    public void addCollection(){
        String message = collectionService.AddCollection();
        System.out.println(message);
    }


    public void updateCollection(){

        System.out.print("Enter ISBN of the collection you want to update: ");
        String isbn = scanner.next();

        String message = collectionService.UpdateCollection(isbn);
        System.out.println(message);

    }

    public void DisplayCollection() throws SQLException {

        System.out.println("Veuillez entrer l'Isbn de collection que vous voulez afficher:");
        String isbn = scanner.next();
        collectionService.DisplayCollectionByIsbn(isbn);

    }


}
