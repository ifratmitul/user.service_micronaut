package service;

import Exceptions.UserNotFoundException;
import jakarta.inject.Singleton;
import model.User;
import model.UserResponse;
import userservice.com.client.Preference;
import userservice.com.client.PreferenceClient;
import userservice.com.repository.UserRepository;

import java.util.*;

@Singleton
public class UserService {

    private final UserRepository _userRepository;
    private final PreferenceClient preferenceClient;

    public UserService(UserRepository userRepository, PreferenceClient preferenceClient) {
        _userRepository = userRepository;
        this.preferenceClient = preferenceClient;
    }

    public User createUser (User user) {
        return _userRepository.save(user);
    }

    public List<User> getUserList () {
        return _userRepository.findAll();
    }

    public User getUserbyId(int id) {
        return _userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    public User updateUser(int id, User user) {
        User u = getUserbyId(id);
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setMobileNumber(user.getMobileNumber());

        return _userRepository.update(u);
    }

    public void deleteUser(int id) {
        _userRepository.deleteById(id);
    }

    public UserResponse getUserDetails (int userId) {
        User user = getUserbyId(userId);
        Optional<Preference> optionalPreference = preferenceClient.getUserPreference(userId);
        Preference preference = optionalPreference.orElse(null);
        return UserResponse.builder()
                .user(user)
                .preference(preference)
                .build();
    }

    public Long getUserCount() {
        return _userRepository.count();
    }
}

