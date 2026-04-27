package com.sistemasdistr.basico;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordCifrada = encoder.encode("admin123");
        System.out.println(passwordCifrada);
    }
}