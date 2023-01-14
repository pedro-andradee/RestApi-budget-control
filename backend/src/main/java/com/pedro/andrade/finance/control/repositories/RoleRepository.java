package com.pedro.andrade.finance.control.repositories;

import com.pedro.andrade.finance.control.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
