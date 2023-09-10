package org.example.Repository;

import org.example.Helpers.DbFunctions;
import org.example.Model.Emprunteur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpruntRepository {
    DbFunctions database = new DbFunctions();

    Connection connection = database.connect_to_db();





}
