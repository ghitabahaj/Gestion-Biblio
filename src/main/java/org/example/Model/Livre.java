package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Livre {
    private long Id;
    private String numLivre;
    private Collection collection;

    private Status status;

    private List<Emprunt> empruntList;
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.
                status = status;
    }
    public Livre(Long Id ,String numLivre, Collection collection, Status status) {
        this.Id = Id;
        this.numLivre = numLivre;
        this.collection = collection;
        this.status = status;
    }

    public Livre(){};
    public long getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNumLivre() {
        return numLivre;
    }

    public void setNumLivre(String numLivre) {
        this.numLivre = numLivre;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public List<Emprunt> getEmpruntList() {
        return empruntList;
    }

    public void setEmpruntList(List<Emprunt> empruntList) {
        this.empruntList = empruntList;
    }

    public Livre mapData(ResultSet resultSet) throws SQLException {

        Collection collection=new Collection();
        Status status=new Status();
        this.numLivre = resultSet.getString("numeroInventair");
        this.collection.setId(resultSet.getLong("collection_id"));
        this.collection=collection.mapData(resultSet);
        this.status.setId(resultSet.getLong("status_id"));
        this.status=status.mapData(resultSet);
        return this;
    }



}
