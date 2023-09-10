package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Status {

    private long Id;
    private String Label;

    public Status(String label) {
        Label = label;
    }

    public Status() {}

    public Long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }


    public Status mapData(ResultSet resultSet) throws SQLException {

        this.Label = resultSet.getString("label");

        return this;
    }


}
