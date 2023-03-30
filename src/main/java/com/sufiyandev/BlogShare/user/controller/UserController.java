package com.sufiyandev.BlogShare.user.controller;

import com.sufiyandev.BlogShare.exception.UserException;
import com.sufiyandev.BlogShare.security.JWTService;
import com.sufiyandev.BlogShare.user.dto.UserCreation;
import com.sufiyandev.BlogShare.user.dto.UserResponse;
import com.sufiyandev.BlogShare.user.model.User;
import com.sufiyandev.BlogShare.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/all")
    private ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/signup")
    private ResponseEntity<UserResponse> createUser(@RequestBody UserCreation userCreation) {
        User user = userService.createUser(userCreation);
        UserResponse userResponse = userService.userToUserResponse(user);
        userResponse.setToken(
                jwtService.createJwt(user.getId())
        );
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    private ResponseEntity<UserResponse> login(@RequestBody UserCreation userCreation) {
        User user = userService.login(userCreation);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setToken(
                jwtService.createJwt(user.getId())
        );
        return ResponseEntity.ok(userResponse);
    }

    @ExceptionHandler({
            UserService.UserNotFoundException.class,
            UserService.InvalidUserException.class})
    private ResponseEntity<UserException> HandleUserException(Exception ex) {
        HttpStatus status;
        String message;

        if (ex instanceof UserService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof UserService.InvalidUserException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_ACCEPTABLE;
        } else {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        UserException userException = UserException.builder()
                .message(message).build();

        return ResponseEntity.status(status).body(userException);
    }


}
