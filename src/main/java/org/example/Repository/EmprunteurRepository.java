package org.example.Repository;

import org.example.Helpers.DbFunctions;
import org.example.Model.Emprunteur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmprunteurRepository {
    DbFunctions database = new DbFunctions();

    Connection connection = database.connect_to_db();

    private Scanner scanner;


    public boolean saveEmprunteur(Emprunteur emprunteur) throws SQLException {

        String insertEmprunteurQuery = "INSERT INTO Emprunteur (membreship, fullname, email, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertEmprunteurQuery)) {
            preparedStatement.setString(1, emprunteur.getMembership());
            preparedStatement.setString(2,emprunteur.getNom());
            preparedStatement.setString(3, emprunteur.getEmail());
            preparedStatement.setString(4, emprunteur.getTelephone());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        }
    }

    public boolean updateEmprunteur(Emprunteur emprunteur) throws SQLException {
        String updateEmprunteurQuery = "UPDATE Emprunteur Set  fullname=?, email=?, phone=? WHERE membreship=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateEmprunteurQuery)) {

            preparedStatement.setString(1,emprunteur.getNom());
            preparedStatement.setString(2, emprunteur.getEmail());
            preparedStatement.setString(3, emprunteur.getTelephone());
            preparedStatement.setString(4, emprunteur.getMembership());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    private Emprunteur createNewEmprunteur(String name , String adresss , String Phone) {
        Scanner scanner = new Scanner(System.in);

        Emprunteur emprunteur = new Emprunteur();
        emprunteur.setNom(name);
        emprunteur.setEmail(adresss);
        emprunteur.setTelephone(Phone);

        return emprunteur;
    }
    public List<Emprunteur> findAll() throws SQLException{

        List<Emprunteur> emprunteurs=new ArrayList<>();
        String SelectEmprunteurQuery="Select * from Emprunteur";

        try(PreparedStatement preparedStatement=connection.prepareStatement(SelectEmprunteurQuery);
            ResultSet resultSet =preparedStatement.executeQuery()){
            while (resultSet.next()) {
                Emprunteur emprunteur =new Emprunteur();
                emprunteur.setId(resultSet.getLong("id"));
                emprunteurs.add(emprunteur.mapData(resultSet));
            }
        }
        return emprunteurs;
    }

    public Emprunteur findByMembreShip(String MembreShip)throws SQLException{
        String MembreShipEmprunteurQuery="Select * from Emprunteur where membreship=?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(MembreShipEmprunteurQuery)){
            preparedStatement.setString(1,MembreShip);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                Emprunteur emprunteur=new Emprunteur();
                emprunteur.setId(resultSet.getLong("id"));
                emprunteur.mapData(resultSet);
                return emprunteur;
            }
        }
        return null;
    }

    public int emprunteurExists(String membreship) {
        String checkEmprunteurQuery = "SELECT COUNT(*) FROM emprunteur WHERE membreship = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(checkEmprunteurQuery)) {
            pstmt.setString(1, membreship);

            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            return resultSet.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean deleteByMembership(String membership) throws SQLException {
        String deleteEmprunteurQuery = "DELETE FROM Emprunteur WHERE membreship = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteEmprunteurQuery)) {
            preparedStatement.setString(1, membership);

            int rowDeleted = preparedStatement.executeUpdate();
            return rowDeleted > 0;
        }
    }


}
