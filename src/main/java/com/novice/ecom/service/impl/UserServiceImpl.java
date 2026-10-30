package com.novice.ecom.service.impl;

import com.novice.ecom.entity.User;
import com.novice.ecom.repo.UserRepo;
import com.novice.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

//    List<User> userList = new ArrayList<>();
//    private long userId = 1;

    @Override
    public List<User> fetchAllUsers() {
//        return userList;

        return userRepo.findAll();
    }

    @Override
    public void createNewUser(User user) {
//        user.setId(userId++);
//        userList.add(user);

        userRepo.save(user);
    }

    @Override
    public Optional<User> fetchUser(Long id) {

//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();

        return userRepo.findById(id);
    }

    @Override
    public boolean updateInfo(Long id, User updatedUser) {
        log.info("Updating the user with id {}",id);

//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst()
//                .map(existingUser -> {
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());
//                    existingUser.setEmail(updatedUser.getEmail());
//                    return true;
//                })
//                .orElse( false);

        return userRepo.findById(id)
                .map( existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setEmail(updatedUser.getEmail());
                    userRepo.save(existingUser);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteUser(Long id) {
        boolean exists = userRepo.existsById(id);

        if (exists) {
            userRepo.deleteById(id);
            return true;
        }
        else
            return false;
    }
}
