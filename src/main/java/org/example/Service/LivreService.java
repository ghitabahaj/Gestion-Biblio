package org.example.Service;

import org.example.Repository.LivreRepository;

public class LivreService {

    private LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository){this.livreRepository = livreRepository;}



    public boolean AddBook(String num){
        if(livreRepository.checkIfBookExistsByNUM(num) ){
            System.out.println("Livre avec ce Numero d'inventaire " + num + " existe déja!");
            return false;

        }else{
            livreRepository.addBook(num);
            return true;
        }
    }
    public void DisplayBook(String title){
         if(livreRepository.findBookByTitle(title)!=null){
             livreRepository.displayBookByTitle(title);
         }else{
             System.out.println("Un Livre avec Ce Titre :" + title + "  n'existe pas!!!!!!!");
         }
    }

    public void DisplayAllBooks(){
        livreRepository.displayAllBooks();
    }


    public void DeleteBook(String numLivre){
        if(livreRepository.DeleteBook(numLivre)){
            System.out.println("Book Deleted Successfully\n");
        }else {
            System.out.println("Failed to delete the book");
        }
    }


    public void DisplayBooksBorrowed(){
        livreRepository.displayBorrowedBooksInfo();
    }

}
