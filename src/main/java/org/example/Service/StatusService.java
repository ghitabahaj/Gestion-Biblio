package org.example.Service;

import org.example.Repository.StatusRepository;

import java.sql.SQLException;

public class StatusService {


    private StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository){ this.statusRepository = statusRepository;}

    public boolean AddNewStatus(String Label) throws SQLException {
        if (statusRepository.StatusExists(Label) > 0) {
            System.out.println("Status with label " + Label + " already exists");
            return false;
        } else {
            boolean added = statusRepository.addStatus(Label);
            if (added) {
                System.out.println("Status added successfully.");
            } else {
                System.out.println("Failed to add the status.");
            }
            return added;
        }
    }


}
