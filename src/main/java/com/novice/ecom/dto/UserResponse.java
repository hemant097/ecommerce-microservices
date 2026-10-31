package com.novice.ecom.dto;

import com.novice.ecom.enums.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private UserRole userRole;

    private AddressDto address;
}
