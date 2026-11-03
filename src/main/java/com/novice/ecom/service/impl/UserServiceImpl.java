package com.novice.ecom.service.impl;

import com.novice.ecom.dto.AddressDto;
import com.novice.ecom.dto.UserRequest;
import com.novice.ecom.dto.UserResponse;
import com.novice.ecom.entity.Address;
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
    public List<UserResponse> fetchAllUsers() {
//        return userList;

        return userRepo.findAll().stream()
                .map(user -> mapToUserResponse(user))
                .toList();
    }

    @Override
    public void createNewUser(UserRequest userRequest) {
//        user.setId(userId++);
//        userList.add(user);

        User user = new User();
        updateUserFromRequest(userRequest, user);
        userRepo.save(user);
    }

    @Override
    public Optional<UserResponse> fetchUser(Long id) {

//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();

        return userRepo.findById(id)
                .map(this::mapToUserResponse);//Optional.map wraps the return value of mapToUserResponse in an Optional
    }

    @Override
    public boolean updateInfo(Long id, UserRequest updatedUser) {
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
                    updateUserFromRequest(updatedUser, existingUser);
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

    private UserResponse mapToUserResponse(User user){
        UserResponse userResponse =  new UserResponse();

        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setMobileNumber(user.getMobileNumber());
        userResponse.setUserRole(user.getUserRole());

        if(user.getAddress() != null){
            AddressDto addressResponse = new AddressDto(user.getAddress().getStreet(),
                    user.getAddress().getCity(),
                    user.getAddress().getState(),
                    user.getAddress().getCountry(),
                    user.getAddress().getZipcode());

            userResponse.setAddress(addressResponse);
        }

        return userResponse;
    }

    private void updateUserFromRequest(UserRequest userRequest, User user){
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setMobileNumber(userRequest.getMobileNumber());
        user.setUserRole(userRequest.getUserRole());

        if(userRequest.getAddress()!=null){
            Address address = new Address();
            address.setStreet(user.getAddress().getStreet());
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setCountry(user.getAddress().getCountry());
            address.setZipcode(user.getAddress().getZipcode());
        }
    }
}
