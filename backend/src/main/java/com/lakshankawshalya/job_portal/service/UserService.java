package com.lakshankawshalya.job_portal.service;

import com.lakshankawshalya.job_portal.dto.RegisterRequest;
import com.lakshankawshalya.job_portal.entity.Role;
import com.lakshankawshalya.job_portal.entity.RoleType;
import com.lakshankawshalya.job_portal.entity.User;
import com.lakshankawshalya.job_portal.repository.RoleRepository;
import com.lakshankawshalya.job_portal.repository.UserRepository;
import com.lakshankawshalya.job_portal.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Register new User
    public String registerUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Assign Role
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(RoleType.valueOf(request.getRole()))
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
        return "User registered successfully!";
    }

    // Authenticate and Generate JWT Token
    public String authenticate(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return jwtUtil.generateToken(username);
        }
        throw new RuntimeException("Invalid Credentials");
    }
}
