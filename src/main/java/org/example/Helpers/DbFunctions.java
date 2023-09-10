package org.example.Helpers;


import java.sql.Connection;
import java.sql.DriverManager;

public class DbFunctions {

    private String database = "bibliotheque";

    private String password = "password";

    private String user = "postgres";
    public Connection connect_to_db() {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, user, password);
            if (conn != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
