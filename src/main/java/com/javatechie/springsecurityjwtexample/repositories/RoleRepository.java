package com.javatechie.springsecurityjwtexample.repositories;

import com.javatechie.springsecurityjwtexample.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<AppRole,Long> {
    public AppRole findAppRoleByRoleName(String strName);
}
