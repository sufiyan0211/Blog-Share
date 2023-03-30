package com.sufiyandev.BlogShare.user.service;

import com.sufiyandev.BlogShare.user.dto.UserCreation;
import com.sufiyandev.BlogShare.user.dto.UserResponse;
import com.sufiyandev.BlogShare.user.model.User;
import com.sufiyandev.BlogShare.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public User createUser(UserCreation userCreation) {
        if(userCreation.getUsername() == null || userCreation.getPassword() == null) throw new UserNotFoundException();
        User user = modelMapper.map(userCreation, User.class);
        user.setPassword(passwordEncoder.encode(userCreation.getPassword()));
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
        if(userCreation.getUsername() == null || userCreation.getPassword() == null) throw new UserNotFoundException();
        User user = findByUsername(userCreation.getUsername());
        if(!passwordEncoder.matches(userCreation.getPassword(), user.getPassword())) throw new InvalidUserException();
        return user;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
    }


    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("Username: " + username + " not found in the database.");
        }

        public UserNotFoundException(Long userId) {
            super("UserId: " + userId + " not found in the database.");
        }

        public UserNotFoundException() {
            super("username and password can not empty");
        }
    }

    public static class InvalidUserException extends IllegalArgumentException {
        public InvalidUserException() {
            super("username and password does not match.");
        }
        public InvalidUserException(Long userId) {
            super(String.format("UserId %s have write access." , userId));
        }


    }


}
