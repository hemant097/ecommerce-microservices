package com.novice.ecom.service;

import com.novice.ecom.entity.User;

import java.util.List;

public interface UserService {

    List<User> fetchAllUsers();

    void createNewUser(User user);

    User fetchUser(Long id);
}