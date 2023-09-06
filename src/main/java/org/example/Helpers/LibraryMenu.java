package org.example.Helpers;
import org.example.Model.Collection;

import java.util.Scanner;

public class LibraryMenu {
    private Scanner scanner;
    private DbFunctions libraryDatabase;

    private String database = "bibliotheque";

    private String password = "password";

    private String user = "postgres";
    public LibraryMenu() {
        scanner = new Scanner(System.in);
        libraryDatabase = new DbFunctions();

    }

    public void displayMenu() {
        boolean exit = false;
        Collection collection = new Collection();
        while (!exit) {
            System.out.println("**********\nLibrary Management Menu:**********");
            System.out.println("1. Ajouter Un Nouveau Livre");
            System.out.println("2. Supprimer Un Livre");
            System.out.println("3. Modifier Un Livre");
            System.out.println("4. Afficher Tout Les Livres ");
            System.out.println("5. Rechercher Un Livre");
            System.out.println("6. Ajouter Une Nouvelle Collection");
            System.out.println("7. Afficher Une collection a partir d'Isbn");
            System.out.println("8. Emprunter Un Livre");
            System.out.println("9. Afficher Les Livres Empruntés et Les Informations d'Emprunteur ");
            System.out.println("10. Ajouter Un Nouveau Status");
            System.out.println("11. Afficher Les Statistiques");
            System.out.println("12. Exit");

            System.out.print("Enter your choice: ");

            // Check if there is an integer available to read
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("ME ");
                        break;
                    case 2:
                        System.out.print("ME ");
                        break;
                    case 3:
                        System.out.print("ME ");
                        break;
                    case 4:
                        System.out.print("ME ");
                        break;
                    case 5:
                        System.out.print("ME");
                        break;
                    case 6:
                        System.out.print("***** L'ajout d'une nouvelle collection *****");
                        if (collection.addCollection(libraryDatabase.connect_to_db(database,user,password), scanner)) {
                            System.out.println("Collection added successfully.");
                        } else {
                            System.out.println("Failed to add collection.");
                        }
                        break;
                    case 7:
                        System.out.print("ME ");
                        break;
                    case 8:
                        System.out.print("ME ");
                        break;
                    case 9:
                        System.out.print("ME ");
                        break;
                    case 10:
                        System.out.print("ME ");
                        break;
                    case 11:
                        System.out.print("ME ");
                        break;
                    case 12:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                // Clear the invalid input from the scanner
                scanner.next();
                System.out.println("Invalid input. Please enter a number.");
            }
        }


    }


    }
