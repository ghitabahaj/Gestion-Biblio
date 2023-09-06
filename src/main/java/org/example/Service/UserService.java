package org.example.Service;

import org.example.Model.User;
import org.example.Repository.UserRepository;

public class UserService {
    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticateUser(User userN) {
        User user = userRepository.findByUsername(userN.getUsername());
        if (user != null && user.getPassword().equals(userN.getPassword())) {
           return user;

        } else{
          return null;
        }
    }
}
