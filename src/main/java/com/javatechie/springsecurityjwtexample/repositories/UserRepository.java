package com.javatechie.springsecurityjwtexample.repositories;

import com.javatechie.springsecurityjwtexample.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
//    public AppUser findByUsername(String strName);

    public AppUser findByEmail(String strName);
}
