package com.lakshankawshalya.job_portal.repository;

import com.lakshankawshalya.job_portal.entity.Role;
import com.lakshankawshalya.job_portal.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
