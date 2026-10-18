package com.novice.ecom.controller;

import com.novice.ecom.entity.User;
import com.novice.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getAllUsers(@PathVariable Long id){
        return new ResponseEntity<>(userService.fetchUser(id), HttpStatus.CREATED);
    }

    @PostMapping("/api/users")
    public String createUser(@RequestBody User user){
        userService.createNewUser(user);
        return "new User added successfully";
    }

}
