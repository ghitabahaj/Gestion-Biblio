package org.example.Repository;

import org.example.Helpers.DbFunctions;
import org.example.Model.Emprunt;
import org.example.Model.Emprunteur;
import org.example.Model.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntRepository {
    DbFunctions database = new DbFunctions();

    Connection connection = database.connect_to_db();

    public Boolean saveEmprunt(Emprunt emprunt, List<Long> ids) {
        String sql = "INSERT INTO Emprunt (startDate, endDate, returne, emprunteur_id) VALUES (?, ?, ?, ?)";
        String insertLivreEmpruntSql = "INSERT INTO LivreEmprunt (livre_id, emprunt_id) VALUES (?, ?)";

        try (PreparedStatement empruntPreparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement livreEmpruntPreparedStatement = connection.prepareStatement(insertLivreEmpruntSql)) {
            empruntPreparedStatement.setDate(1, (Date) emprunt.getDateDebut());
            empruntPreparedStatement.setDate(2, (Date) emprunt.getDateRetour());
            empruntPreparedStatement.setBoolean(3, emprunt.getRetourLivre());
            empruntPreparedStatement.setInt(4, Math.toIntExact(emprunt.getEmprunteur().getId()));
            empruntPreparedStatement.executeUpdate();
            int affectedRows = empruntPreparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            try (var generatedKeys = empruntPreparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int empruntId = generatedKeys.getInt(1);
                    for(Long id : ids) {
                        livreEmpruntPreparedStatement.setLong(1, id);
                        livreEmpruntPreparedStatement.setInt(2, empruntId);
                        livreEmpruntPreparedStatement.executeUpdate();
                    }
                } else {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public boolean update(Long id)throws SQLException{
        String UpdateEmpruntQuery="Update Emprunt Set returne=? Where id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(UpdateEmpruntQuery)){
            preparedStatement.setBoolean(1,true);
            preparedStatement.setLong(2,id);

            int rowDeleted=preparedStatement.executeUpdate();
            return rowDeleted > 0;
        }
    }


    public List<Emprunt> findByAll()throws SQLException{
        List<Emprunt> emprunts=new ArrayList<>();
        List<Livre> livres=new ArrayList<>();
        String findByAllEmpruntQuery="Select * From Emprunt ";
        String findByIdLivresQuery="SELECT l.* , C.*\n" +
                "FROM emprunt e\n" +
                "JOIN livreEmprunt el ON e.id = el.emprunt_id\n" +
                "JOIN livre l ON el.livre_id = l.id\n" +
                "JOIN Collection C On l.collection_id=C.id \n"+
                "WHERE e.id =?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(findByAllEmpruntQuery);
            ResultSet resultSet= preparedStatement.executeQuery()){

            while(resultSet.next()){
                Emprunt emprunt=new Emprunt();

                emprunts.add(emprunt.mapData(resultSet));
                try(PreparedStatement preparedStatementL=connection.prepareStatement(findByIdLivresQuery)){
                    preparedStatement.setLong(1,resultSet.getLong("id"));
                    ResultSet resultSetL= preparedStatementL.executeQuery();
                    while(resultSetL.next()){
                        Livre livre=new Livre();
                        livre.setId(resultSetL.getLong("id"));
                        livres.add(livre.mapData(resultSetL));
                    }
                    emprunt.setLivres(livres);
                }

            }
            return emprunts;
        }
    }

    public Emprunt findById(Long id)throws SQLException{
        List<Livre> livres=new ArrayList<>();
        String findByIdLivresQuery="SELECT l.* , C.*\n" +
                "FROM emprunt e\n" +
                "JOIN livreEmprunt el ON e.id = el.emprunt_id\n" +
                "JOIN livre l ON el.livre_id = l.id\n" +
                "JOIN Collection C On l.collection_id=C.id \n"+
                "WHERE e.id =?";
        String findByIdEmpruntQuery="Select * From Emprunt Where id=?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(findByIdEmpruntQuery);PreparedStatement preparedStatementL=connection.prepareStatement(findByIdLivresQuery)){
            preparedStatement.setLong(1,id);
            preparedStatementL.setLong(1,id);

            ResultSet resultSet= preparedStatement.executeQuery();
            ResultSet resultSetL= preparedStatementL.executeQuery();
            if(resultSet.next()){
                Emprunt emprunt=new Emprunt();
                Livre livre=new Livre();
                emprunt.mapData(resultSet);
                while(resultSetL.next()){
                    livre.setId(resultSetL.getLong("id"));
                    livres.add(livre.mapData(resultSetL));
                }
                emprunt.setLivres(livres);
                return emprunt;
            }
        }
        return null;
    }

    public Emprunt findEmpruntLivre(Livre livre){
        String sql="Select e.* from emprunt e join livreemprunt le on e.id=le.emprunt_id join livre l on le.livre_id=l.id where l.id=?";
        try (PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setLong(1,livre.getId());
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                Emprunt emprunt=new Emprunt();
                emprunt.mapData(resultSet);
                return emprunt;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public boolean delete(Long id)throws SQLException{

        String deleteLivreEmprunt="Delete from LivreEmprunt where livre_id=?";
        try( PreparedStatement preparedStatementS = connection.prepareStatement(deleteLivreEmprunt)){
            preparedStatementS.setLong(1,id);

            int rowDeleted=preparedStatementS.executeUpdate();
            return rowDeleted > 0;
        }
    }

    public Integer checkCount(Long id){
        String sql="Select count(*) from livreemprunt where emprunt_id=?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

}
