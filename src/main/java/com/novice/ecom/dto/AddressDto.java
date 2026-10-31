package com.novice.ecom.dto;

public record AddressDto (String street,
                          String city,
                          String state,
                          String country,
                          String zipcode)
{}