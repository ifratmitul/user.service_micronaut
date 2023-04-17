package service;

import jakarta.inject.Singleton;
import model.User;

import java.util.*;

@Singleton
public class UserService {
    private final List<User> users = new ArrayList<>();

    public User createUser (User user) {
        users.add(user);
        return user;
    }

    public List<User> getUserList () {
        return this.users;
    }

    public User getUserbyId(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    public User updateUser(int id, User user) {
        User u = getUserbyId(id);

        if(u == null) {
            return null;
        }
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setMobileNumber(user.getMobileNumber());
        return u;
    }

    public String deleteUser(int id) {
        if(getUserbyId(id) != null) {
            users.removeIf(u -> u.getId() == id);
            return "Removed successfully";

        }

        return "Not found";
    }




    }

