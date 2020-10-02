package services;

import models.User;

import java.util.ArrayList;
import java.util.List;

public class LoginService {
    static List<User> userList = new ArrayList<>();
    static {
        userList.add(new User("123","123", "email1@gmail.com"));
        userList.add(new User("234","234","email2@gmail.com"));
    }
    public boolean login(String email, String password){
        User userCandidate = new User(email, password);
        for(User user: userList){
            //переписать на логин и пароль
            if(user.getEmail().equals(userCandidate.getEmail()) && (user.getPassword().equals(userCandidate.getPassword()))) return true;
        }
        return false;
    }
}