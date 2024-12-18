package com.example.vipa.validators;

import org.springframework.stereotype.Service;

@Service
public class PasswordValidator {

    private static final String PASSWORD_TOO_SHORT = "Пароль слишком короткий. Минимальная длина - 6 символов.";
    private static final String PASSWORD_WEAK = "Пароль слишком слабый. Используйте хотя бы одну цифру и специальный символ.";

    public void validatePassword(String password) {
        if (password.length() < 6) {
            throw new IllegalArgumentException(PASSWORD_TOO_SHORT);
        }
        if (!password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new IllegalArgumentException(PASSWORD_WEAK);
        }
    }
}
