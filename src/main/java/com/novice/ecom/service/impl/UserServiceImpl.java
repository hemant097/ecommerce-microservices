package com.novice.ecom.service.impl;

import com.novice.ecom.entity.User;
import com.novice.ecom.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    List<User> userList = new ArrayList<>();
    private long userId = 1;

    @Override
    public List<User> fetchAllUsers() {
        return userList;
    }

    @Override
    public void createNewUser(User user) {
        user.setId(userId++);
        userList.add(user);
    }

    @Override
    public Optional<User> fetchUser(Long id) {

        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                ;
    }

    @Override
    public boolean updateInfo(Long id, User updatedUser) {

        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setEmail(updatedUser.getEmail());
                    return true;
                })
                .orElse( false)
                ;
    }
}
