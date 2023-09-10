package org.example.Service;

import org.example.Model.Collection;
import org.example.Repository.CollectionRepository;
import org.example.Repository.LivreRepository;

import java.sql.SQLException;

public class CollectionService {

    private CollectionRepository collectionRepository;

    public CollectionService(CollectionRepository collectionRepository){this.collectionRepository = collectionRepository;}

    private Collection collection = new Collection();

    public void DisplayCollectionByIsbn(String isbn) throws SQLException {
        if(collectionRepository.collectionExists(isbn) == 0){
            System.out.println("Collection with ISBN " + isbn + " does not exist.");
        }else{
            collection = collectionRepository.getCollectionByIsbn(isbn);
            System.out.println("Collection Details:");
            System.out.println("+-------------------+-----------------+");
            System.out.println("| ISBN              | " + collection.getIsbn());
            System.out.println("| Title             | " + collection.getTitre());
            System.out.println("| Author            | " + collection.getAuteur());
            System.out.println("| Total Books       | " + collection.getNbrLivre());
            System.out.println("+-------------------+-----------------+");
        }
    }
}
