package com.javatechie.springsecurityjwtexample.services;

import com.javatechie.springsecurityjwtexample.entities.AppRole;
import com.javatechie.springsecurityjwtexample.entities.AppUser;
import com.javatechie.springsecurityjwtexample.repositories.RoleRepository;
import com.javatechie.springsecurityjwtexample.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public AppUser SaveUser(AppUser user) {
        String hashPw = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPw);
        return userRepository.save(user);
    }

    public void SaveRole(AppRole role) {
        if (roleRepository.findAppRoleByRoleName(role.getRoleName()) == null) {
            roleRepository.save(role);
        }
    }

    public void addRoleToUser(String strUserEmail, String strRoleName) {
        AppRole appRole = roleRepository.findAppRoleByRoleName(strRoleName);
        AppUser user = userRepository.findByEmail(strUserEmail);
        user.getRoles().add(appRole);
        userRepository.save(user);
    }

//    public AppUser findUserByUserName(String strUserName) {
//        return userRepository.findByUsername(strUserName);
//    }

    public AppUser findUserByEmail(String strUserName) {
        return userRepository.findByEmail(strUserName);
    }

    public AppUser findById(long lngId) {

        return userRepository.getOne(lngId);
    }
}
