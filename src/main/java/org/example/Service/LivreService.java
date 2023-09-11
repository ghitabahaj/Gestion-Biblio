package org.example.Service;

import org.example.Repository.LivreRepository;

public class LivreService {

  LivreRepository livreRepository = new LivreRepository();

    public boolean AddBook(String num){
        if(livreRepository.checkIfBookExistsByNUM(num) ){
            System.out.println("Livre avec ce Numero d'inventaire " + num + " existe déja!");
            return false;

        }else{
            livreRepository.addBook(num);
            return true;
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

    public void DisplayAllBooks(){
        livreRepository.displayAllBooks();
    }


    public String DeleteBook(String numLivre){
        if(livreRepository.DeleteBook(numLivre)){
            return "Book Deleted Successfully";
        }else {
           return "Failed to delete the book";
        }
    }


    public void DisplayBooksBorrowed(){
        livreRepository.displayBorrowedBooksInfo();
    }

}
