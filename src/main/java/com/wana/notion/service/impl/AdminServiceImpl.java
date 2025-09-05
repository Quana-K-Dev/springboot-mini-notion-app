package com.wana.notion.service.impl;

import com.wana.notion.dto.AdminUserDto;
import com.wana.notion.entity.rbac.Role;
import com.wana.notion.entity.rbac.UserRole;
import com.wana.notion.entity.rbac.UserRoleId;
import com.wana.notion.entity.user.User;
import com.wana.notion.repository.RoleRepository;
import com.wana.notion.repository.UserRepository;
import com.wana.notion.repository.UserRoleRepository;
import com.wana.notion.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserDto> getAllAdmins() {
        log.debug("Getting all admin users");
        var adminUsers = userRepository.findAllAdminsFetched(); // Sử dụng fetch để tránh LazyInitializationException
        return adminUsers.stream()
                .map(this::convertToAdminDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminUserDto> getAllUsersForRoleManagement(Pageable pageable) {
        log.debug("Getting users page for role management: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        var usersPage = userRepository.findAllBy(pageable); // Sử dụng @EntityGraph
        var userDtos = usersPage.getContent().stream()
                .map(this::convertToAdminDto)
                .collect(Collectors.toList());
        return new PageImpl<>(userDtos, pageable, usersPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public AdminUserDto getUserDetails(Long userId) {
        log.debug("Getting user details for userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Để tránh LazyInitializationException, có thể tạo method riêng với @EntityGraph
        // hoặc fetch trong query. Ở đây ta làm đơn giản bằng cách force load
        user.getUserRoles().size(); // Force lazy loading trong transaction
        return convertToAdminDto(user);
    }

    @Override
    @Transactional
    public void assignAdminRole(Long userId) {
        log.info("Assigning admin role to userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Role adminRole = roleRepository.findByCode("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        // Kiểm tra xem user đã có role admin chưa
        UserRole existingUserRole = userRoleRepository.findByUserIdAndRoleCode(userId, "ROLE_ADMIN");
        if (existingUserRole != null) {
            throw new RuntimeException("User already has admin role");
        }

        UserRole userRole = UserRole.builder()
                .id(new UserRoleId(userId, adminRole.getId()))
                .user(user)
                .role(adminRole)
                .grantedAt(Instant.now())
                .build();

        userRoleRepository.save(userRole);
        log.info("Admin role assigned successfully to userId: {}", userId);
    }

    @Override
    @Transactional
    public void revokeAdminRole(Long userId) {
        log.info("Revoking admin role from userId: {}", userId);

        // Kiểm tra không được xóa admin mặc định
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if ("admin@notion.com".equals(user.getEmail())) {
            throw new RuntimeException("Cannot revoke admin role from system administrator");
        }

        UserRole existingUserRole = userRoleRepository.findByUserIdAndRoleCode(userId, "ROLE_ADMIN");
        if (existingUserRole == null) {
            throw new RuntimeException("User does not have admin role");
        }

        userRoleRepository.deleteByUserIdAndRoleCode(userId, "ROLE_ADMIN");
        log.info("Admin role revoked successfully from userId: {}", userId);
    }

    @Override
    @Transactional
    public void promoteUserToSeller(Long userId) {
        log.info("Promoting user to seller, userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Role sellerRole = roleRepository.findByCode("ROLE_SELLER")
                .orElseThrow(() -> new RuntimeException("Seller role not found"));

        // Kiểm tra xem user đã có role seller chưa
        UserRole existingUserRole = userRoleRepository.findByUserIdAndRoleCode(userId, "ROLE_SELLER");
        if (existingUserRole != null) {
            throw new RuntimeException("User already has seller role");
        }

        UserRole userRole = UserRole.builder()
                .id(new UserRoleId(userId, sellerRole.getId()))
                .user(user)
                .role(sellerRole)
                .grantedAt(Instant.now())
                .build();

        userRoleRepository.save(userRole);
        log.info("User promoted to seller successfully, userId: {}", userId);
    }

    @Override
    @Transactional
    public void demoteSellerToUser(Long userId) {
        log.info("Demoting seller to user, userId: {}", userId);

        UserRole existingUserRole = userRoleRepository.findByUserIdAndRoleCode(userId, "ROLE_SELLER");
        if (existingUserRole == null) {
            throw new RuntimeException("User does not have seller role");
        }

        userRoleRepository.deleteByUserIdAndRoleCode(userId, "ROLE_SELLER");
        log.info("Seller demoted to user successfully, userId: {}", userId);
    }

    private AdminUserDto convertToAdminDto(User user) {
        List<String> roles = user.getUserRoles().stream()
                .map(ur -> ur.getRole().getCode())
                .collect(Collectors.toList());

        return AdminUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .avatarUrl(user.getAvatarUrl())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .roles(roles)
                .isAdmin(roles.contains("ROLE_ADMIN"))
                .isSeller(roles.contains("ROLE_SELLER"))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public long countAllUsers() {
        return userRepository.count(); // đếm toàn bộ user
    }
}