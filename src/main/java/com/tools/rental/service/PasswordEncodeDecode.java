package com.tools.rental.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 * Argon2PasswordEncoder has parameters
 * int saltLength,
 * int hashLength,
 * int parallelism,
 * int memory,
 * int iterations
 * <p>
 * Place the encoder in this class so that any changes to the parameters are handled in one spot.
 */
public class PasswordEncodeDecode {
    private PasswordEncodeDecode() {
    }

    public static String encode(String myPassword) {
        final var encoder = new Argon2PasswordEncoder();

        return encoder.encode(myPassword);
    }

    public static boolean matches(String myPassword, String encodedPassword) {
        final var encoder = new Argon2PasswordEncoder();

        return encoder.matches(myPassword, encodedPassword);
    }
}
