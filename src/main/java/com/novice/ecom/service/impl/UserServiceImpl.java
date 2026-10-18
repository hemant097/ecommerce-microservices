package com.novice.ecom.service.impl;

import com.novice.ecom.entity.User;
import com.novice.ecom.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public User fetchUser(Long id) {
        for(User u: userList){
            if(u.getId().equals(id))
                return u;
        }
        return null;
    }
}
