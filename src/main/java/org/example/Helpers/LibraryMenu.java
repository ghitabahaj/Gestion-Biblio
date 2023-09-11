package org.example.Helpers;
import org.example.Controller.CollectionController;
import org.example.Controller.EmpruntController;
import org.example.Controller.EmprunteurController;
import org.example.Controller.LivreController;
import org.example.Model.Collection;
import org.example.Model.Livre;
import org.example.Repository.CollectionRepository;
import org.example.Repository.LivreRepository;
import org.example.Repository.StatusRepository;
import org.example.Service.CollectionService;
import org.example.Service.EmpruntService;
import org.example.Service.LivreService;
import org.example.Service.StatusService;

import java.sql.SQLException;
import java.util.Scanner;

public class LibraryMenu {
    private Scanner scanner;
    private DbFunctions libraryDatabase;

    LivreRepository livreRepository = new LivreRepository();


    StatusRepository statusRepository = new StatusRepository();

    StatusService statusService = new StatusService(statusRepository);

    EmprunteurController emprunteurController = new EmprunteurController();

    EmpruntController empruntController = new EmpruntController();

    LivreController livreController = new LivreController();

    CollectionController collectionController = new CollectionController();
    public LibraryMenu() {
        scanner = new Scanner(System.in);
        libraryDatabase = new DbFunctions();

    }

    public void displayMenu() throws SQLException {
        boolean exit = false;
        Collection collection = new Collection();
        Livre livre = new Livre();
        while (!exit) {
            System.out.println("**********\nLibrary Management Menu:**********");
            System.out.println("1. Ajouter Un Nouveau Livre");
            System.out.println("2. Supprimer Un Livre");
            System.out.println("3. Modifier Une Collection");
            System.out.println("4. Afficher Un Livre à partir de son Titre");
            System.out.println("5. Afficher Un Livre à partir de son Auteur");
            System.out.println("6. Afficher Tout Les Livres Disponibles");
//            System.out.println("7. Ajouter Une Nouvelle Collection");
            System.out.println("8. Afficher Une collection a partir d'Isbn");
            System.out.println("9. Ajouter Un Nouveau Emprunteur");
            System.out.println("10. Afficher La liste des emprunterus");
            System.out.println("11. Modifier Les Informations d'un Emprunteur");
            System.out.println("12. Supprimer Un emprunteur");
            System.out.println("13. Emprunter Un Livre");
            System.out.println("14. Afficher Les Livres Empruntés et Les Informations d'Emprunteur ");
//            System.out.println("15. Ajouter Un Nouveau Status");
            System.out.println("16. Afficher Les Statistiques");
            System.out.println("17. Retourner Un Livre");
            System.out.println("18. Exit");

            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("***** L'ajout d'un nouveau livre *****\n");
                        livreController.AddLivre();
                        break;
                    case 2:
                        System.out.print("------- Suppression du livre à partir du numéro d'inventaire ! --------\n");
                        livreController.delete();
                        break;
                    case 3:
                        System.out.print("-------- Mise à Jour De Collection --------\n ");
                        collectionController.updateCollection();
                        break;
                    case 4:
                        System.out.print("***** L'affichage d'un livre à partir de son titre*****\n");
                        livreController.DisplayByTitle();

                        break;
                    case 5:
                        System.out.print("***** L'affichage d'un livre à partir de son auteur*****\n");
                        break;
                    case 6:
                        System.out.print("***** L'affichage e tout les livres Disponibles *****\n");
                        livreController.DisplayAllBooks();
                        break;
               /*     case 7:
                        System.out.print("***** L'ajout d'une nouvelle collection *****\n");
                        collectionService.AddCollection();
                        break;*/
                    case 8:
                        System.out.println("------- Affichage du Collection à partir d'Isbn --------\n");
                        collectionController.DisplayCollection();

                        break;
                    case 9:
                        System.out.print("------------ L'ajout d'un nouveau Emprunteur ------------\n ");
                        emprunteurController.save();
                        break;
                    case 10:
                        System.out.print("------------ La Liste Des Emprunteurs ------------\n ");
                        emprunteurController.findAll();
                        break;
                    case 11:
                        System.out.print("------------ Modification d'un Emprunteur ------------\n ");
                        emprunteurController.update();
                        break;
                    case 12:
                        System.out.print("------------ Suprression d'un Emprunteur  ------------\n ");
                        emprunteurController.delete();
                        break;
                    case 13:
                        System.out.print("------------ Emprunter Un Livre ------------\n ");
                       empruntController.addEmprunt();
                        break;
                    case 14:
                        System.out.print("---------- Affichage Des Livres Empruntés avec Les informations des Emprunteurs ----------\n ");
                         livreController.DisplayBorrowedBooks();
                        break;
//                    case 15:
//                        System.out.print("--------- Ajout d'un nouveau Status --------- \n");
//                        System.out.println("Veuillez entrer label de nouveau status:");
//                        String label = scanner.next();
//                        statusService.AddNewStatus(label);
//
//                        break;
                    case 16:
                        System.out.print("-------- L'affichage des statistiques -----------\n");
                        livreRepository.displayBookStatistics();
                        break;
                    case 17:
                        System.out.print("-------- Retourner Un Livre -----------\n");
                        empruntController.Returned();
                        break;
                    case 18:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.\n");
                }
            } else {
                // Clear the invalid input from the scanner
                scanner.next();
                System.out.println("Invalid input. Please enter a number.\n");
            }
        }


    }


    }
