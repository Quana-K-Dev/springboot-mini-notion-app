package com.wana.notion.config;

import com.wana.notion.entity.rbac.Role;
import com.wana.notion.entity.rbac.UserRole;
import com.wana.notion.entity.rbac.UserRoleId;
import com.wana.notion.entity.user.User;
import com.wana.notion.entity.user.UserStatus;
import com.wana.notion.repository.RoleRepository;
import com.wana.notion.repository.UserRepository;
import com.wana.notion.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Tạo roles nếu chưa tồn tại
        createRoleIfNotExists("ROLE_USER", "User");
        createRoleIfNotExists("ROLE_ADMIN", "Administrator");
        createRoleIfNotExists("ROLE_SELLER", "Seller");

        // Tạo admin user mặc định nếu chưa tồn tại
        createDefaultAdminIfNotExists();
    }

    private void createRoleIfNotExists(String code, String name) {
        if (roleRepository.findByCode(code).isEmpty()) {
            Role role = Role.builder()
                    .code(code)
                    .name(name)
                    .build();
            roleRepository.save(role);
        }
    }

    private void createDefaultAdminIfNotExists() {
        if (userRepository.findByEmail("admin@notion.com").isEmpty()) {
            User adminUser = User.builder()
                    .email("admin@notion.com")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .displayName("System Administrator")
                    .status(UserStatus.ACTIVE)
                    .userRoles(new HashSet<>())
                    .build();

            userRepository.save(adminUser);

            // Assign admin role
            Role adminRole = roleRepository.findByCode("ROLE_ADMIN").orElseThrow();
            UserRole userRole = UserRole.builder()
                    .id(new UserRoleId(adminUser.getId(), adminRole.getId()))
                    .user(adminUser)
                    .role(adminRole)
                    .grantedAt(Instant.now())
                    .build();

            userRoleRepository.save(userRole);
        }
    }
}
