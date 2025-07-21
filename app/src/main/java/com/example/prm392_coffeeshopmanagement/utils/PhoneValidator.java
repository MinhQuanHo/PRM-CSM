package com.example.prm392_coffeeshopmanagement.utils;

public class PhoneValidator {
    private static final String PHONE_REGEX = "^(03|05|07|08|09)\\d{8}$";

    public static boolean isValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return phoneNumber.matches(PHONE_REGEX);
    }
}
