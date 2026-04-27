package com.sistemasdistr.basico.config;

import com.sistemasdistr.basico.model.User;
import com.sistemasdistr.basico.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        System.out.println("DEBUG username recibido: " + username);
        System.out.println("DEBUG usuario encontrado: " + user);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        System.out.println("DEBUG username BD: " + user.getUsername());
        System.out.println("DEBUG password BD: " + user.getPassword());
        System.out.println("DEBUG role BD: " + user.getUserRole().getRoleName());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getUserRole().getRoleName()))
        );
    }
}
