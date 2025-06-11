package main;

import domain.User;
import service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService.createUser(new User(0, "Bob", "Bobson"));
        UserService.readUsers();
        UserService.updateUser(new User(1, "Tony", "Smith"));
        UserService.deleteUser(1);

    }
}
