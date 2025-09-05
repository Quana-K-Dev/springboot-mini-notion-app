package com.wana.notion.service;

import com.wana.notion.dto.AdminUserDto;
import com.wana.notion.dto.RoleAssignmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    List<AdminUserDto> getAllAdmins();
    long countAllUsers();
    Page<AdminUserDto> getAllUsersForRoleManagement(Pageable pageable);
    void assignAdminRole(Long userId);
    void revokeAdminRole(Long userId);
    void promoteUserToSeller(Long userId);
    void demoteSellerToUser(Long userId);
    AdminUserDto getUserDetails(Long userId);
}
