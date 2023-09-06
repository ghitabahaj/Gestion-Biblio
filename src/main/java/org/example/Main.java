package org.example;

import org.example.Helpers.DbFunctions;
import org.example.Helpers.LibraryMenu;
import org.example.Model.User;
import org.example.Repository.UserRepository;
import org.example.Service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DbFunctions db = new DbFunctions();

        UserRepository userRepository = new UserRepository(db.connect_to_db("bibliotheque","postgres","password"));

        UserService userService = new UserService(userRepository);


        Scanner scanner = new Scanner(System.in);

        User userA = new User();
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        userA.setUsername(username);
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        userA.setPassword(password);


       User user = userService.authenticateUser(userA);


        if (user !=null ) {
            System.out.println("Authentication successful.\n Hello " + user.getUsername()+ "!");
            LibraryMenu menu = new LibraryMenu();

            System.out.print("Enter your choice: ");
            menu.displayMenu();

        } else {
            System.out.println("Invalid Information");

        }
    }


    }

