package com.tools.rental.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/">how
 * to validate password with regular expression</a>
 */
public class PasswordValidator {

    // digit + lowercase char + uppercase char + punctuation + symbol
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isNotValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }

}
