package org.example.Service;

import org.example.Model.Collection;
import org.example.Repository.CollectionRepository;
import org.example.Repository.LivreRepository;

import java.sql.SQLException;

public class CollectionService {

     CollectionRepository collectionRepository = new CollectionRepository();


    private Collection collection = new Collection();

    public String DisplayCollectionByIsbn(String isbn) throws SQLException {
        if(collectionRepository.collectionExists(isbn) == 0){
         return "Collection with ISBN " + isbn + " does not exist.";
        }else{
            collection = collectionRepository.getCollectionByIsbn(isbn);
            System.out.println("Collection Details:");
            System.out.println("+-------------------+-----------------+");
            System.out.println("| ISBN              | " + collection.getIsbn());
            System.out.println("| Title             | " + collection.getTitre());
            System.out.println("| Author            | " + collection.getAuteur());
            System.out.println("| Total Books       | " + collection.getNbrLivre());
            System.out.println("+-------------------+-----------------+");

            return "Done";
        }
    }

    public String AddCollection(){
        if (collectionRepository.addCollection()) {
            return "Collection added successfully.";
        } else {
            return  "Failed to add collection.";
        }
    }

    public String UpdateCollection(String isbn){
        if (collectionRepository.collectionExists(isbn)>0){
             collectionRepository.updateCollection(isbn);
             return "Collection updated successufully";
        }else {
            return "Failed to update the collection,or collection does not exists";
        }
    }








}
