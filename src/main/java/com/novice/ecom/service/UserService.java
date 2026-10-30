package com.novice.ecom.service;

import com.novice.ecom.entity.User;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> fetchAllUsers();

    void createNewUser(User user);

    Optional<User> fetchUser(Long id);

    boolean updateInfo(Long id, User updatedUser);

    boolean deleteUser(Long id);
}