package com.wana.notion.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class RegisterRequest {

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 254, message = "Email không được vượt quá 254 ký tự")
    private String email;

    @Size(max = 80, message = "Tên hiển thị không được vượt quá 80 ký tự")
    private String displayName;

    @Size(max = 160, message = "Giới thiệu không được vượt quá 160 ký tự")
    private String bio;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword;

    // Custom validation method
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}