package com.wana.notion.repository;

import com.wana.notion.entity.rbac.UserRole;
import com.wana.notion.entity.rbac.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    @Query("SELECT ur FROM UserRole ur WHERE ur.user.id = :userId")
    List<UserRole> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM UserRole ur WHERE ur.user.id = :userId AND ur.role.code = :roleCode")
    void deleteByUserIdAndRoleCode(@Param("userId") Long userId, @Param("roleCode") String roleCode);

    @Query("SELECT ur FROM UserRole ur WHERE ur.user.id = :userId AND ur.role.code = :roleCode")
    UserRole findByUserIdAndRoleCode(@Param("userId") Long userId, @Param("roleCode") String roleCode);
}
