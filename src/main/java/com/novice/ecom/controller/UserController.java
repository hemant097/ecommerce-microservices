package com.novice.ecom.controller;

import com.novice.ecom.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final List<User> userList=new ArrayList<>();

    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        return userList;
    }

    @PostMapping("/api/users")
    public List<User> createUser(@RequestBody User user){
        userList.add(user);
        return userList;

    }

}
