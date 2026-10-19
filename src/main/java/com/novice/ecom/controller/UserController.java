package com.novice.ecom.controller;

import com.novice.ecom.entity.User;
import com.novice.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<User> getUser(@PathVariable Long id){

//        User user = userService.fetchUser(id);
//
//        if(user==null)
//            return ResponseEntity.notFound().build();
//
//        return new ResponseEntity<>(user, HttpStatus.OK);

        return userService.fetchUser(id)
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.createNewUser(user);
        return new ResponseEntity<>("new User added successfully", HttpStatus.CREATED);
    }

}
