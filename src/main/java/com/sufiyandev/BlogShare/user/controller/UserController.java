package com.sufiyandev.BlogShare.user.controller;

import com.sufiyandev.BlogShare.exception.UserException;
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

    @GetMapping("")
    private ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/signup")
    private ResponseEntity<UserResponse> createUser(@RequestBody UserCreation userCreation) {
        User user = userService.createUser(userCreation);
        UserResponse userResponse = userService.userToUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/signin")
    private ResponseEntity<UserResponse> login(@RequestBody UserCreation userCreation) {
        User user = userService.login(userCreation);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(userResponse);
    }

    @ExceptionHandler({
            UserService.UserCreationException.class,
            UserService.UserNotFoundException.class})
    private ResponseEntity<UserException> HandleUserException(Exception ex) {
        HttpStatus status;
        String message;
        if (ex instanceof UserService.UserCreationException) {
            message = ex.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        else if (ex instanceof UserService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        UserException userException = UserException.builder()
                .message(message).build();

        return ResponseEntity.status(status).body(userException);
    }


}
