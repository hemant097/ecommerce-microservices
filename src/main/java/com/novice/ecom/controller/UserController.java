package com.novice.ecom.controller;

import com.novice.ecom.dto.UserRequest;
import com.novice.ecom.dto.UserResponse;
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
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){

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
    public ResponseEntity<String> createUser(@RequestBody UserRequest user){
        userService.createNewUser(user);
        return new ResponseEntity<>("new User added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInfo(@PathVariable Long id,
                                           @RequestBody UserRequest updatedUserRequest){
        boolean updated =  userService.updateInfo(id, updatedUserRequest);

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
