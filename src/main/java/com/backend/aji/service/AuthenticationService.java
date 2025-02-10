//package com.backend.aji.service;
//
//import com.backend.aji.dto.LoginUserDto;
//import com.backend.aji.dto.RegisterUserDto;
//import com.backend.aji.entity.User;
//import com.backend.aji.repository.UserRepository;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class AuthenticationService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//
//    public AuthenticationService(
//            UserRepository userRepository,
//            AuthenticationManager authenticationManager,
//            PasswordEncoder passwordEncoder
//    ) {
//        this.authenticationManager = authenticationManager;
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public User signup(RegisterUserDto input) {
//        var user = new User()
//                .setFullName(input.getFullName())
//                .setEmail(input.getEmail())
//                .setPassword(passwordEncoder.encode(input.getPassword()));
//
//        return userRepository.save(user);
//    }
//
//    public User authenticate(LoginUserDto input) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        input.getEmail(),
//                        input.getPassword()
//                )
//        );
//
//        return userRepository.findByEmail(input.getEmail()).orElseThrow();
//    }
//
//    public List<User> allUsers() {
//        List<User> users = new ArrayList<>();
//
//        userRepository.findAll().forEach(users::add);
//
//        return users;
//    }
//}
package com.backend.aji.service;

import com.backend.aji.dto.LoginUserDto;
import com.backend.aji.dto.RegisterUserDto;
import com.backend.aji.entity.User;
import com.backend.aji.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PersistenceContext
    private EntityManager entityManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        var user = new User()
                .setFullName(input.getFullName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return getUserByEmail(input.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

//    public List<User> allUsers() {
//        return userRepository.findAll();
//    }

    private Optional<User> getUserByEmail(String email) {
        try {
            String query = "SELECT u FROM User u WHERE u.email = :email";
            User user = entityManager.createQuery(query, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
