package com.example.prm392_coffeeshopmanagement.utils;

public class PasswordUtils {
    public static boolean validatePassword(String password) {
        // 1. Độ dài >= 6
        if (password == null || password.length() < 6) {
            return false;
        }

        // 2. Chứa ít nhất 1 ký tự chữ cái (a-zA-Z)
        boolean hasLetter = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
                break;
            }
        }
        if (!hasLetter) {
            return false;
        }

        // 3. Chứa ít nhất 1 ký tự đặc biệt
        String specialChars = "!@#$%^&*()_+{}|:\"<>?`~[]\\;',./-=";
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (specialChars.indexOf(c) != -1) {
                hasSpecial = true;
                break;
            }
        }

        return hasSpecial;
    }
}
