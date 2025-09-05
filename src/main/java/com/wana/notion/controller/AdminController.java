package com.wana.notion.controller;

import com.wana.notion.dto.AdminUserDto;
import com.wana.notion.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("")
    public String adminDashboard(Model model) {
        List<AdminUserDto> admins = adminService.getAllAdmins();
        long usersCount = adminService.countAllUsers();

        model.addAttribute("admins", admins);
        model.addAttribute("usersCount", usersCount);
        return "admin/dashboard";
    }


    @GetMapping("/users")
    public String manageUsers(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminUserDto> usersPage = adminService.getAllUsersForRoleManagement(pageable);

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());

        return "admin/users";
    }

    @PostMapping("/assign-admin")
    public String assignAdmin(@RequestParam Long userId, RedirectAttributes redirectAttributes) {
        try {
            adminService.assignAdminRole(userId);
            redirectAttributes.addFlashAttribute("successMessage", "Admin role assigned successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/revoke-admin")
    public String revokeAdmin(@RequestParam Long userId, RedirectAttributes redirectAttributes) {
        try {
            adminService.revokeAdminRole(userId);
            redirectAttributes.addFlashAttribute("successMessage", "Admin role revoked successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/promote-seller")
    public String promoteSeller(@RequestParam Long userId, RedirectAttributes redirectAttributes) {
        try {
            adminService.promoteUserToSeller(userId);
            redirectAttributes.addFlashAttribute("successMessage", "User promoted to seller successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/demote-seller")
    public String demoteSeller(@RequestParam Long userId, RedirectAttributes redirectAttributes) {
        try {
            adminService.demoteSellerToUser(userId);
            redirectAttributes.addFlashAttribute("successMessage", "Seller demoted to user successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}
