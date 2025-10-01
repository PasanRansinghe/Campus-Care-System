package com.ruhuna.campuscaresystem.service;

import com.ruhuna.campuscaresystem.model.User;
import com.ruhuna.campuscaresystem.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public AuthService(UserRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo; this.encoder = encoder;
    }

    public User registerUser(String email, String password, String name) {
        if (repo.existsByEmail(email)) throw new RuntimeException("Email already exists");
        User u = new User(email, encoder.encode(password), name);
        return repo.save(u);
    }

    public User authenticateUser(String email, String rawPassword) {
        User u = repo.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!encoder.matches(rawPassword, u.getPassword()))
            throw new RuntimeException("Invalid credentials");
        return u;
    }
}