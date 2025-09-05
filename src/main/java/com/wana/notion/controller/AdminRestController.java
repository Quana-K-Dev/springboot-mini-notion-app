package com.wana.notion.controller;

import com.wana.notion.dto.AdminUserDto;
import com.wana.notion.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final AdminService adminService;

    @GetMapping("/users/search")
    public ResponseEntity<List<AdminUserDto>> searchUsers(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String role) {

        // Implementation for searching users based on criteria
        List<AdminUserDto> users = adminService.getAllAdmins(); // Simplified for demo
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<AdminUserDto> getUserDetails(@PathVariable Long userId) {
        AdminUserDto user = adminService.getUserDetails(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users/{userId}/toggle-status")
    public ResponseEntity<String> toggleUserStatus(@PathVariable Long userId) {
        // Implementation for toggling user status
        return ResponseEntity.ok("User status toggled successfully");
    }
}
