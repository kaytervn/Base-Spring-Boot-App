package com.app.repository;

import com.app.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    Optional<Permission> findFirstByName(String name);
    Optional<Permission> findFirstByPermissionCode(String code);
    Optional<Permission> findFirstByAction(String action);
}
