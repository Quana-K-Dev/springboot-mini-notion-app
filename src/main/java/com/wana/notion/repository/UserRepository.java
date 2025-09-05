package com.wana.notion.repository;

import com.wana.notion.entity.user.User;
import com.wana.notion.entity.user.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // Dùng cho Security
    @EntityGraph(attributePaths = {"userRoles", "userRoles.role"})
    Optional<User> findOneWithRolesByEmail(String email);

    Page<User> findByStatusOrderByCreatedAtDesc(UserStatus status, Pageable pageable);

    // Danh sách theo role (không phân trang) – fetch để tránh LIE khi map DTO
    @Query("""
        select distinct u from User u
        join fetch u.userRoles ur
        join fetch ur.role r
        where r.code = :roleCode
        order by u.createdAt desc
    """)
    List<User> findUsersByRoleFetched(@Param("roleCode") String roleCode);

    // Admin list (không phân trang) – fetch
    @Query("""
        select distinct u from User u
        join fetch u.userRoles ur
        join fetch ur.role r
        where r.code = 'ROLE_ADMIN'
        order by u.createdAt desc
    """)
    List<User> findAllAdminsFetched();

    // Phân trang + entity graph (KHÔNG dùng fetch join với page)
    @EntityGraph(attributePaths = {"userRoles", "userRoles.role"})
    Page<User> findAllBy(Pageable pageable);
}