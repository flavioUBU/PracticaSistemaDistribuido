package com.sistemasdistr.basico.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.sistemasdistr.basico.model.Role;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
