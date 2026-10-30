package com.novice.ecom.controller;

import com.novice.ecom.entity.User;
import com.novice.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){

//        User user = userService.fetchUser(id);
//        if(user==null)
//            return ResponseEntity.notFound().build();
//        else
//            return new ResponseEntity<>(user, HttpStatus.OK);

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.createNewUser(user);
        return new ResponseEntity<>("new User added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInfo(@PathVariable Long id,
                                           @RequestBody User updatedUser){
        boolean updated =  userService.updateInfo(id, updatedUser);

        if(updated)
            return ResponseEntity.ok("User updated successfully");

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInfo(@PathVariable Long id){
        boolean isDeleted = userService.deleteUser(id);

        if(isDeleted)
            return ResponseEntity.ok("User deleted successfully");
        return ResponseEntity.notFound().build();

    }

}
