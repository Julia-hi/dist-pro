package com.stpg.distrinet.repository;

import com.stpg.distrinet.models.ennum.ERole;
import com.stpg.distrinet.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);

    int count();
}
