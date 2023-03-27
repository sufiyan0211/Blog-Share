package com.sufiyandev.BlogShare.user.service;

import com.sufiyandev.BlogShare.user.dto.UserCreation;
import com.sufiyandev.BlogShare.user.dto.UserResponse;
import com.sufiyandev.BlogShare.user.model.User;
import com.sufiyandev.BlogShare.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public User createUser(UserCreation userCreation) {
        if(userCreation.getUsername() == null || userCreation.getPassword() == null) throw new UserCreationException();
        User user = modelMapper.map(userCreation, User.class);
        userRepository.save(user);
        return user;
    }

    public UserResponse userToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        if (user.getUsername() != null) {
            userResponse.setUsername(user.getUsername());
        }
        if (user.getName() != null) {
            userResponse.setName(user.getName());
        }
        if (user.getId() != null) {
            userResponse.setId(user.getId());
        }
        if (user.getBio() != null) {
            userResponse.setBio(user.getBio());
        }
        if (user.getEmail() != null) {
            userResponse.setEmail(user.getEmail());
        }
        return userResponse;
    }

    public User login(UserCreation userCreation) {
        if(userCreation.getUsername() == null || userCreation.getPassword() == null) throw new UserCreationException();
        User user = findByUsername(userCreation.getUsername());
        // TODO: Password validations
        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("Username: " + username + " not found in the database.");
        }
    }

    public static class UserCreationException extends IllegalArgumentException {
        public UserCreationException() {
            super("username and password can not empty");
        }
    }

}
