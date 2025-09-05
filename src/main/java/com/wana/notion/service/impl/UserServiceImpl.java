package com.wana.notion.service.impl;

import com.wana.notion.dto.RegisterRequest;
import com.wana.notion.entity.rbac.Role;
import com.wana.notion.entity.rbac.UserRole;
import com.wana.notion.entity.rbac.UserRoleId;
import com.wana.notion.entity.user.User;
import com.wana.notion.entity.user.UserStatus;
import com.wana.notion.repository.RoleRepository;
import com.wana.notion.repository.UserRepository;
import com.wana.notion.repository.UserRoleRepository;
import com.wana.notion.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerUser(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        // Validate email doesn't exist
        if (isEmailExists(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại trong hệ thống");
        }

        // Validate password confirmation
        if (!request.isPasswordMatching()) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp");
        }

        // Create new user
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .displayName(request.getDisplayName())
                .bio(request.getBio())
                .status(UserStatus.ACTIVE)
                .userRoles(new HashSet<>())
                .build();

        // Save user first to get the ID
        user = userRepository.save(user);
        log.info("User created with ID: {}", user.getId());

        // Assign default USER role
        Role userRole = roleRepository.findByCode("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        UserRole userRoleAssignment = UserRole.builder()
                .id(new UserRoleId(user.getId(), userRole.getId()))
                .user(user)
                .role(userRole)
                .grantedAt(Instant.now())
                .build();

        userRoleRepository.save(userRoleAssignment);
        log.info("ROLE_USER assigned to user: {}", user.getEmail());

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}