package com.sistemasdistr.basico;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCheck {
    public static void main(String[] args) {
        String passwordPlano = "admin123";
        String hashGuardado = "$2a$10$UW4yHPecfjY/vWKYMpKvn.QH7xy9dD800H98ofmSeiY0D8hjYIsEi";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean coincide = encoder.matches(passwordPlano, hashGuardado);

        System.out.println("¿Coincide? " + coincide);
    }
}