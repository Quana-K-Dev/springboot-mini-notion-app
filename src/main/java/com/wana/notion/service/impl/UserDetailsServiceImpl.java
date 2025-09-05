package com.wana.notion.service.impl;

import com.wana.notion.entity.user.User;
import com.wana.notion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true) // <<< QUAN TRỌNG
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOneWithRolesByEmail(email) // <<< dùng method có fetch
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(getAuthorities(user))
                .accountExpired(false)
                .accountLocked("BLOCKED".equals(user.getStatus().name()))
                .credentialsExpired(false)
                .disabled("PENDING_VERIFICATION".equals(user.getStatus().name()))
                .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getUserRoles().stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getRole().getCode()))
                .collect(Collectors.toUnmodifiableSet());
    }
}
