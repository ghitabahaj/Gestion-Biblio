package org.example.Service;

import org.example.Model.Livre;
import org.example.Repository.CollectionRepository;
import org.example.Repository.LivreRepository;

public class LivreService {

  LivreRepository livreRepository = new LivreRepository();

  CollectionRepository collectionRepository = new CollectionRepository();

    public String AddBook(String num){
        if(livreRepository.checkIfBookExistsByNUM(num) ){
            System.out.println("Livre avec ce Numero d'inventaire " + num + " existe déja!");
            return "Try Again";

        }else{
            livreRepository.addBook(num);
            return "Book Added Successufully";
        }
    }
    public String DisplayBook(String title){
         if(livreRepository.findBookByTitle(title)!=null){
             livreRepository.displayBookByTitle(title);
             return "Done";
         }else{
            return "Un Livre avec Ce Titre :" + title + "  n'existe pas!!!!!!!";
         }
    }

    public String DisplayAllBooks(){
        if(livreRepository.CountLivres()>0){
            livreRepository.displayAllBooks();
            return "Done";
        }else {
            return "There's no Book";
        }

    }


    public String DeleteBook(String numLivre){
        if(livreRepository.DeleteLivre(numLivre)){
            return "Book Deleted Successfully";
        }else {
           return "Failed to delete the book";
        }
    }


    public String DisplayBooksBorrowed(){
       if(livreRepository.CountBorrowedBooks()>0){
           livreRepository.displayBorrowedBooksInfo();
           return "done";
       }else{
           return "There s no Borrowed Books";
       }
    }



    public String DeleteBookC(String numLivre) {
        if (livreRepository.CheckIfBorrowed(numLivre) != null) {
            return "This book is already borrowed and cannot be deleted.";
        } else {
           Livre livre = livreRepository.findNumInventaire(numLivre);
            long collectionId = livre.getCollection().getId();

            if (livre.getCollection().getNbrLivre() == 1) {
                  collectionRepository.DeleteCollection(collectionId);
                  return "Book has been deleted successufully";
            }
            if(livreRepository.DeleteLivre(numLivre) ) {
                return "Book has been deleted successufully";
            }
            return "Error has been occured , Failed to delete the book";
        }
    }

}
