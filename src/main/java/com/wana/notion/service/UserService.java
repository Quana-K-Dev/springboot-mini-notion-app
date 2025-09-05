package com.wana.notion.service;

import com.wana.notion.dto.RegisterRequest;
import com.wana.notion.entity.user.User;

public interface UserService {
    User registerUser(RegisterRequest request);
    boolean isEmailExists(String email);
}