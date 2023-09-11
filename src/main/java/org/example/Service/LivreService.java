package org.example.Service;

import org.example.Repository.LivreRepository;

public class LivreService {

  LivreRepository livreRepository = new LivreRepository();

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
        if(livreRepository.DeleteBook(numLivre)){
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

}
