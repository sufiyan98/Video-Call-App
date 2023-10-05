package com.sufi.videocall.Entity;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class UserService {

    private static final List<User> USERS_LIST = new ArrayList<>();

    public void register(User user) {
        user.setStatus("online");
        USERS_LIST.add(user);
    }

    public User login(User user) {
        System.out.println("Received login request for email: " + user.getEmail());

        var userIndex = IntStream.range(0, USERS_LIST.size())
                .filter(i -> {
                    String userEmail = USERS_LIST.get(i).getEmail();
                    System.out.println("Comparing with stored email: " + userEmail);
                    return userEmail.equals(user.getEmail());
                })
                .findAny()
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("User found with index: " + userIndex);

        var cUser = USERS_LIST.get(userIndex);

        if (!cUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Password incorrect");
        }

        cUser.setStatus("online");
        System.out.println("User logged in successfully: " + cUser.getEmail());
        return cUser;
    }


    public void logout(String email) {
        var userIndex = IntStream.range(0, USERS_LIST.size())
                .filter(i -> USERS_LIST.get(i).getEmail().equals(email))
                .findAny()
                .orElseThrow(() -> new RuntimeException("User not found"));
        USERS_LIST.get(userIndex).setStatus("offline");
    }

    public List<User> findAll() {
        return USERS_LIST;
    }
}