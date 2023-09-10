package org.example.Service;


import org.example.Model.Collection;
import org.example.Model.Emprunteur;
import org.example.Repository.EmprunteurRepository;

import java.sql.SQLException;
import java.util.List;


public class EmprunteurService {

    EmprunteurRepository emprunteurRepository = new EmprunteurRepository();

    public boolean save(Emprunteur emprunteur) throws SQLException {
        String membership = emprunteur.getMembership();

        if (emprunteurRepository.emprunteurExists(membership) > 0) {
            System.out.println("Emprunteur with membership " + membership + " already exists.");
            return false;
        } else {
            boolean res = emprunteurRepository.saveEmprunteur(emprunteur);
            if (res) {
                System.out.println("Emprunteur saved successfully.");
                return true;
            }

            System.out.println("Failed to save the Emprunteur.");
            return false;
        }


    }

    public String update(Emprunteur emprunteur) throws SQLException {
        if (emprunteurRepository.emprunteurExists(emprunteur.getMembership()) == 0) {
            return "Emprunteur with membership " + emprunteur.getMembership() + " does not exists.";

        } else {
            boolean res = emprunteurRepository.updateEmprunteur(emprunteur);
            if (res) {
                return "this Emprunteur is successfully updated";
            }
            return "an error occurred while updating this Emprunteur try again";
        }

    }

//    public boolean delete(Long id)throws SQLException{
//        boolean res= emprunteurRepository.delete(id);
//        if(res){
//            return true;
//        }
//        return false;
//    }

    public List<Emprunteur> findAll() throws SQLException {
        List<Emprunteur> emprunteurs = emprunteurRepository.findAll();
        if (emprunteurs != null) {
            return emprunteurs;
        } else {
            return null;
        }
    }

    public Emprunteur findByMemberShip(String membership) throws SQLException {
        Emprunteur emprunteur = emprunteurRepository.findByMembreShip(membership);
        if (emprunteur != null) {
            return emprunteur;
        } else {
            return null;
        }
    }

    public boolean deleteByMembership(String membership) throws SQLException {

        if (emprunteurRepository.emprunteurExists(membership)== 0) {
            System.out.println("Emprunteur with membership " + membership + " does not exists.");
            return false;

        }else{
            emprunteurRepository.deleteByMembership(membership);
            return true;
        }

    }








}
