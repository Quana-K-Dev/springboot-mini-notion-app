package com.wana.notion.controller;

import com.wana.notion.dto.RegisterRequest;
import com.wana.notion.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model, Authentication authentication) {
        // If user is already authenticated and is admin, redirect to admin dashboard
        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin";
        }

        // If user is authenticated but not admin, redirect to access denied
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/access-denied";
        }

        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute RegisterRequest registerRequest,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        log.info("Processing registration for email: {}", registerRequest.getEmail());

        // Check for validation errors
        if (result.hasErrors()) {
            model.addAttribute("registerRequest", registerRequest);
            return "register";
        }

        // Check if passwords match
        if (!registerRequest.isPasswordMatching()) {
            model.addAttribute("errorMessage", "Mật khẩu xác nhận không khớp");
            model.addAttribute("registerRequest", registerRequest);
            return "register";
        }

        try {
            userService.registerUser(registerRequest);
            log.info("User registration successful for: {}", registerRequest.getEmail());

            redirectAttributes.addFlashAttribute("successMessage",
                    "Đăng ký thành công! Bạn có thể đăng nhập ngay bây giờ.");
            return "redirect:/login";

        } catch (Exception e) {
            log.error("Registration failed for {}: {}", registerRequest.getEmail(), e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("registerRequest", registerRequest);
            return "register";
        }
    }
}